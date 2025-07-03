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

    /** 默认错误码 + 状态，方便只传 message 的场景 */
    public BusinessException(String msg) {
        this(400, msg, HttpStatus.BAD_REQUEST);
    }


    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }
}