// src/main/java/com/example/advisor_backend/security/JwtTokenProvider.java
package com.example.advisor_backend.security;

import com.example.advisor_backend.model.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JwtTokenProvider.class);

    /**
     * 从配置里尝试读取；如果配置里没有，就注入成空串 ""，
     * 这样下边的 if 判断才能走到“else”分支。
     */
    @Value("${jwt.secret:}")
    private String jwtSecretBase64;

    @Value("${jwt.expiration:3600000}")
    private long jwtExpirationMs;

    private Key key;

    @PostConstruct
    public void init() {
        if (jwtSecretBase64 != null && !jwtSecretBase64.isBlank()) {
            // 配置里有值，就用它来解码
            byte[] bytes = Base64.getDecoder().decode(jwtSecretBase64);
            key = Keys.hmacShaKeyFor(bytes);
            log.info("Loaded JWT secret from config ({} bits)", bytes.length * 8);
            log.info("JWT expiration (ms) = {}", jwtExpirationMs);
        } else {
            // 配置里没有，就帮你自动生成一把足够 HS512 安全的 key
            key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
            String generated = Base64.getEncoder().encodeToString(key.getEncoded());
            log.warn("No jwt.secret configured → generated new Base64 key:\n{}", generated);
        }
    }

    public String generateToken(String username, Role role) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);
        log.info("Signing new JWT for {} expires at {}", username, exp);
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            log.debug("Invalid JWT: {}", ex.getMessage());
            return false;
        }
    }
}