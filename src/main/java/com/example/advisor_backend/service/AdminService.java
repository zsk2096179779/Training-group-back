// src/main/java/com/example/advisor_backend/service/AdminService.java
package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.UserResponse;

import java.util.List;

public interface AdminService {
    /**
     * 列出系统里所有用户
     */
    List<UserResponse> listAllUsers();
    // TODO: 后续可以加 create/update/delete 用户的方法
}
