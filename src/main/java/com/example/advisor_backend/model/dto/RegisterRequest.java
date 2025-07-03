package com.example.advisor_backend.model.dto;

import lombok.Data;
 
@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
} 