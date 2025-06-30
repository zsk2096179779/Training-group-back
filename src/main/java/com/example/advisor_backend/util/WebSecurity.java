package com.example.advisor_backend.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    // 白名单路径数组
    private static final String[] WHITELIST = {
            "/api/auth/login",
            "/api/public/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/favicon.ico",
            "/error",
            "/strategy-management/**",
            "/strategy-management",
            "/test",
            "/test/**",
            "api/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 使用新式CORS配置（无参数lambda）
                .cors(with -> {})  // 启用CORS，需配合CorsConfigurationSource bean

                // 禁用CSRF
                .csrf(csrf -> csrf.disable())

                // 授权配置
                .authorizeHttpRequests(authorize -> authorize
                        // 使用AntPathRequestMatcher匹配路径
                        .requestMatchers(
                                Arrays.stream(WHITELIST)
                                        .map(AntPathRequestMatcher::antMatcher)
                                        .toArray(AntPathRequestMatcher[]::new)
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // 异常处理配置
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 返回401而不是302重定向
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                );

        return http.build();
    }
}