// src/main/java/com/example/advisor_backend/config/SecurityConfig.java
package com.example.advisor_backend.config;

import com.example.advisor_backend.security.JwtAuthenticationFilter;
import com.example.advisor_backend.security.JwtTokenProvider;
import com.example.advisor_backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider tokenProvider,
                          CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    // 密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 认证管理器，用于 AuthController 的 login 认证
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // JWT 过滤器 Bean
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, userDetailsService);
    }

    // 安全过滤链
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 启用 CORS 并使用下面定义的配置
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // 关闭 CSRF，因为使用的是无状态的 JWT
                .csrf(AbstractHttpConfigurer::disable)
                // 不使用 Session
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 授权规则
                .authorizeHttpRequests(auth -> auth
                        // 认证相关接口全部放行
                        .requestMatchers("/api/auth/**").permitAll()

                        // factor-tree 公共接口
                        .requestMatchers(
                                "/api/factor-tree/listWithTreeName",
                                "/api/factor-tree/listByType",
                                "/api/factor-tree/list",
                                "/api/factor-tree",
                                "/api/factor-tree/children/**"
                        ).permitAll()

                        // funds 模块公共查询
                        .requestMatchers(HttpMethod.GET,
                                "/api/funds/labels",
                                "/api/funds",
                                "/api/funds/*/profile"
                        ).permitAll()
                        // 保存组合需登录
                        .requestMatchers(HttpMethod.POST, "/api/funds/portfolios").authenticated()

                        // 管理后台仅 ADMIN
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")

                        // 其它核心模块需 USER 或 ADMIN
                        .requestMatchers(
                                "/api/factors/**",
                                "/api/strategy-monitoring/**",
                                "/api/strategy-management/**",
                                "/api/strategy-rebalance/**",
                                "/api/trading/**",
                                "/api/trading/error/replace",
                                "/api/trading/errors/fix",
                                "/api/trading/orders/**",
                                "/api/trading/account-rebalance/**",
                                "/api/trading/fund-research/**",
                                "/api/auth/**",
                                "/api/combos/**",
                                "/api/datasource/**",
                                "/api/derived-factor/**",
                                "/api/style-factors/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // 其余 /api/** 任何接口都需登录
                        .requestMatchers("/api/**").authenticated()

                        // 除以上路径外一律拒绝
                        .anyRequest().denyAll()
                )
                // 将 JWT 过滤器插入到 UsernamePasswordAuthenticationFilter 之前
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // CORS 配置：开发环境下允许所有源
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();
        // 开发时方便调试，允许所有域
        cfg.setAllowedOrigins(List.of("http://localhost:5173"));
        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(true);
        cfg.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }
}
