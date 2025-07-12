package com.example.advisor_backend.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {
    @Test
    void testConstructorAndMessage() {
        BusinessException ex = new BusinessException(1001, "错误信息", HttpStatus.BAD_REQUEST);
        assertEquals(1001, ex.getCode());
        assertEquals("错误信息", ex.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatus());
    }
} 