package com.example.advisor_backend.model.dto;

import com.example.advisor_backend.model.dto.entity.User; // 确保导入User实体

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserInfo user;

    public JwtAuthenticationResponse(String accessToken, User userEntity) {
        this.accessToken = accessToken;
        this.user = new UserInfo(userEntity);
    }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }
    public UserInfo getUser() { return user; }
    public void setUser(UserInfo user) { this.user = user; }

    public static class UserInfo {
        private String username;
        private String email;
        private String role; // 关键字段

        public UserInfo(User userEntity) {
            this.username = userEntity.getUsername();
            this.email = userEntity.getEmail();
            this.role = userEntity.getRole().name(); // 关键：加上这一行
        }

        public String getUsername() { return username; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
    }
}