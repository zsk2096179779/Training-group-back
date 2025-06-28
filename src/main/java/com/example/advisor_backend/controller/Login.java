package com.example.advisor_backend.controller;

import com.example.advisor_backend.Server.UserServe;
import com.example.advisor_backend.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class Login {
    @Autowired
    private UserServe userServe;

    @PostMapping("/test")
    public ResponseEntity<?> CheckUser(@RequestBody User user) {
        String username = user.getName();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        username = username.trim();
        user = userServe.getUserByName(username);
        // 检查用户是否存在
        if (user == null) {
            return ResponseEntity.status(404).body("用户不存在");
        }
        // 返回简单的成功响应（包含用户ID）
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("username", user.getName());

        return ResponseEntity.ok(response);
    }
}
