// src/main/java/com/example/advisor_backend/service/CustomUserDetailsService.java
package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.repository.UserRepository;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepo;

    public CustomUserDetailsService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        List<GrantedAuthority> auths =
                List.of(new SimpleGrantedAuthority(u.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), auths);
    }
}
