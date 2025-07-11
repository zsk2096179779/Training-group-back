package com.example.advisor_backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 自定义的 UserDetailsService 接口，继承自 Spring Security 的 UserDetailsService
 */
public interface CustomUserDetailsService extends UserDetailsService {
    // 目前无需额外方法，直接复用 loadUserByUsername
}
