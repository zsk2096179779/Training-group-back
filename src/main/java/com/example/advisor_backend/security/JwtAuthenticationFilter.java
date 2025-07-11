// src/main/java/com/example/advisor_backend/security/JwtAuthenticationFilter.java
package com.example.advisor_backend.security;

import com.example.advisor_backend.service.CustomUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider tokenProvider,
                                   CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        logger.info("【JWT Filter】请求路径：" + request.getRequestURI() + "，Authorization-header=" + header);
        // …下面继续原来的校验逻辑…
        String token = null;
        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
        }
        logger.info("【JWT Filter】提取到 token = " + token);
        boolean valid = false;
        try {
            valid = token != null && tokenProvider.validateToken(token);
        } catch (Exception ex) {
            logger.error("【JWT Filter】校验 token 时抛出异常：", ex);
        }
        logger.info("【JWT Filter】token 校验结果 = " + valid);
        if (token != null && tokenProvider.validateToken(token)) {
            String username = tokenProvider.getUsernameFromToken(token);
            logger.info("【JWT Filter】解析 username = " + username);
            UserDetails ud = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            ud, null, ud.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource()
                    .buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
            logger.info("【JWT Filter】已将 Authentication 写入 SecurityContextHolder");
        }
        chain.doFilter(request, response);
    }
}