// src/main/java/com/example/advisor_backend/model/dto/LoginRequest.java
package com.example.advisor_backend.model.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Setter
@Getter
public class LoginRequest {
    @NotBlank(message="用户名不能为空")
    private String username;

    @NotBlank(message="密码不能为空")
    private String password;

    public LoginRequest() {}

}