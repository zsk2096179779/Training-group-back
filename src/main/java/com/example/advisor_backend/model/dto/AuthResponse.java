// src/main/java/com/example/advisor_backend/model/dto/AuthResponse.java
package com.example.advisor_backend.model.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";

    public AuthResponse(String token) {
        this.token = token;
    }

}


