package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.model.entity.Role;
import com.example.advisor_backend.repository.UserRepository;
import com.example.advisor_backend.service.ServiceImpl.CustomUserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CustomUserDetailsServiceImpl customUserDetailsService;

    @Test
    void loadUserByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
        user.setRole(Role.ROLE_USER);
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        
        UserDetails result = customUserDetailsService.loadUserByUsername("testuser");
        assertNotNull(result);
    }
} 