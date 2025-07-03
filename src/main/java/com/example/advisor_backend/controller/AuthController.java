// src/main/java/com/example/advisor_backend/controller/AuthController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody @Valid RegisterRequest req) {
        authService.register(req);
        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody @Valid LoginRequest req) {
        AuthResponse resp = authService.login(req);
        return ApiResponse.ok(resp);
    }
}
