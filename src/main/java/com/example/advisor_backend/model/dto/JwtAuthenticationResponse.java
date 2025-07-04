package com.example.advisor_backend.model.dto;

import com.example.advisor_backend.model.entity.User; // 确保导入User实体
import lombok.Getter;

@Getter
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private UserInfo user;

    public JwtAuthenticationResponse(String accessToken, User userEntity) {
        this.accessToken = accessToken;
        this.user = new UserInfo(userEntity);
    }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public void setUser(UserInfo user) { this.user = user; }

    @Getter
    public static class UserInfo {
        private final String username;
        private final String email;
        private final String role; // 关键字段

        public UserInfo(User userEntity) {
            this.username = userEntity.getUsername();
            this.email = userEntity.getEmail();
            this.role = userEntity.getRole().name(); // 关键：加上这一行
        }

    }
}