// src/main/java/com/example/advisor_backend/service/AuthService.java
package com.example.advisor_backend.service;

import com.example.advisor_backend.exception.BusinessException;
import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.*;
import com.example.advisor_backend.repository.UserRepository;
import com.example.advisor_backend.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepo,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Transactional
    public void register(RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            throw new BusinessException(1001, "用户名已存在", HttpStatus.BAD_REQUEST);
        }
        if (userRepo.existsByEmail(req.getEmail())) {
            throw new BusinessException(1002, "邮箱已被注册", HttpStatus.BAD_REQUEST);
        }
        if (!req.getPassword().equals(req.getConfirmPassword())) {
            throw new BusinessException(1003, "两次密码不一致", HttpStatus.BAD_REQUEST);
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setRole(Role.ROLE_USER);
        userRepo.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        User u = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new BusinessException(
                        1004, "用户不存在", HttpStatus.UNAUTHORIZED));
        if (!passwordEncoder.matches(req.getPassword(), u.getPassword())) {
            throw new BusinessException(
                    1005, "密码错误", HttpStatus.UNAUTHORIZED);
        }
        String token = tokenProvider.generateToken(u.getUsername(), u.getRole());
        return new AuthResponse(token);
    }
}
