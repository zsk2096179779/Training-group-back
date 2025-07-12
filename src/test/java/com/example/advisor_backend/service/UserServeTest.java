package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServeTest {
    @Mock
    private UserService userService;

    @Test
    void testGetUserByName_found() {
        User user = new User();
        user.setUsername("Alice");
        when(userService.getUserByName("Alice")).thenReturn(user);
        User result = userService.getUserByName("Alice");
        assertNotNull(result);
        assertEquals("Alice", result.getUsername());
    }

    @Test
    void testGetUserByName_notFound() {
        when(userService.getUserByName("Bob")).thenReturn(null);
        User result = userService.getUserByName("Bob");
        assertNull(result);
    }

    @Test
    void testGetUserByName_nullName_emptyString_trimmedName() {
        when(userService.getUserByName(null)).thenReturn(null);
        when(userService.getUserByName("")).thenReturn(null);
        when(userService.getUserByName("   ")).thenReturn(null);
        assertNull(userService.getUserByName(null));
        assertNull(userService.getUserByName(""));
        assertNull(userService.getUserByName("   "));
    }
} 