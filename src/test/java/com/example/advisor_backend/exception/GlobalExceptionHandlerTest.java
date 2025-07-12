package com.example.advisor_backend.exception;

import com.example.advisor_backend.model.dto.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleBusiness() {
        BusinessException ex = new BusinessException(1001, "业务异常", HttpStatus.BAD_REQUEST);
        ResponseEntity<ApiResponse<?>> resp = handler.handleBusiness(ex);
        assertEquals(1001, resp.getBody().getCode());
        assertEquals("业务异常", resp.getBody().getMsg());
        assertEquals(HttpStatus.BAD_REQUEST, resp.getStatusCode());
    }

    @Test
    void testHandleOther() {
        Exception ex = new RuntimeException("未知异常");
        ResponseEntity<ApiResponse<?>> resp = handler.handleOther(ex);
        assertEquals(500, resp.getBody().getCode());
        assertTrue(resp.getBody().getMsg().contains("服务器内部错误"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
    }
} 