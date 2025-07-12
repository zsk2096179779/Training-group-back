package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.math.BigDecimal;

/**
 * 通用分页请求 DTO
 * @param <F> filter 类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO<F> {
    private int page;    // 当前页，从 1 开始
    private int size;    // 每页条数
    private F filter;    // 业务过滤条件
}