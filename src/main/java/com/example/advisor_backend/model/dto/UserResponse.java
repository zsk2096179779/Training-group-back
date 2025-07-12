// src/main/java/com/example/advisor_backend/model/dto/UserResponse.java
package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 管理员查询所有用户时返回的单个用户信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String role;           // e.g. "ROLE_USER" 或 "ROLE_ADMIN"
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
