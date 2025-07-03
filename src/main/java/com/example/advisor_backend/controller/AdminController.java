// src/main/java/com/example/advisor_backend/controller/AdminController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.model.dto.UserResponse;
import com.example.advisor_backend.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<UserResponse>>> listUsers() {
        List<UserResponse> all = adminService.listAllUsers();
        return ResponseEntity.ok(ApiResponse.ok(all));
    }

    // … 以后可以加 create/update/delete 用户接口
}
