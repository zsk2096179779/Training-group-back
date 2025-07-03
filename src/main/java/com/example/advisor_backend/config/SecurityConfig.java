// src/main/java/com/example/advisor_backend/config/SecurityConfig.java
package com.example.advisor_backend.config;

import com.example.advisor_backend.security.JwtAuthenticationFilter;
import com.example.advisor_backend.security.JwtTokenProvider;
import com.example.advisor_backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider tokenProvider,
                          CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter =
                new JwtAuthenticationFilter(tokenProvider, userDetailsService);

        http
                // 允许跨域（开发时 5173 调试用）
                .cors(Customizer.withDefaults())
                // 关闭 CSRF
                .csrf(AbstractHttpConfigurer::disable)
                // 不建立 HTTP Session
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 授权规则
                .authorizeHttpRequests(auth -> auth
                        // 注册、登录 接口放行
                        .requestMatchers("/api/auth/**").permitAll()

                        // 公共查询接口：标签、基金列表 → 放行给所有请求（或可改为 authenticated()）
                        .requestMatchers(HttpMethod.GET, "/api/funds/labels").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/funds").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/funds/*/profile").permitAll()

                        // 保存组合接口：需登录
                        .requestMatchers(HttpMethod.POST, "/api/funds/portfolios").authenticated()

                        // 管理后台只有 ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 其它模块：因子、策略、组合、交易，需登录
                        .requestMatchers("/api/factors/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/strategies/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/portfolios/**").hasAnyRole("USER","ADMIN")
                        .requestMatchers("/api/trades/**").hasAnyRole("USER","ADMIN")

                        // 其余都得登录
                        .anyRequest().authenticated()
                )
                // 把我们的 JWT Filter 加到 Spring Security 过滤链里
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /** 跨域配置，仅开发时放行 localhost:5173 */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
