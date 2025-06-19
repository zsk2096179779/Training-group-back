package com.example.advisor_backend.exception;

import org.springframework.http.HttpStatus;

/**
 * 自定义业务异常，包含错误码和 HTTP 状态
 */
public class BusinessException extends RuntimeException {
    private final int code;
    private final HttpStatus status;

    public BusinessException(int code, String msg, HttpStatus status) {
        super(msg);
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}