package com.example.advisor_backend.exception;

import com.example.advisor_backend.model.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * 捕获并统一处理各种异常，返回 ApiResponse
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 处理自定义的业务异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<?>> handleBusiness(BusinessException ex) {
        ApiResponse<?> body = ApiResponse.error(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(body, ex.getStatus());
    }

    // 处理参数校验失败异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException ex) {
        // 拼接所有字段错误消息
        String errMsg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        ApiResponse<?> body = ApiResponse.error(400, "参数校验失败: " + errMsg);
        return ResponseEntity.badRequest().body(body);
    }

    // 处理其他未捕获的异常
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleOther(Exception ex) {
        // 打印详细错误信息到控制台，便于调试
        ex.printStackTrace();
        ApiResponse<?> body = ApiResponse.error(500, "服务器内部错误: " + ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }
}