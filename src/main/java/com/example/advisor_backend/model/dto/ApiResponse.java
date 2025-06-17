package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通用 API 返回格式
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiResponse<T> {
    private int code;      // 0 成功，非 0 失败
    private String msg;    // 描述
    private T data;        // 返回数据

    // 成功，无数据
    public static ApiResponse<Void> ok() {
        ApiResponse<Void> r = new ApiResponse<>();
        r.setCode(0);
        r.setMsg("success");
        return r;
    }

    // 成功，有数据
    public static <T> ApiResponse<T> ok(T data) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(0);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    // 失败
    public static <T> ApiResponse<T> error(int code, String msg) {
        ApiResponse<T> r = new ApiResponse<>();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }
}
