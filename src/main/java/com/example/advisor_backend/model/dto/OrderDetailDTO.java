package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
public class OrderDetailDTO {
    // 1）前端传入
    private String fundCode;
    private BigDecimal amount;

    // 2）入库后要写回的自增主键
    private Long id;
    // 3）保存时要设置的其他属性
    private String accountId;
    private String status;
    private LocalDateTime createTime;
    private String fundId;
    private BigDecimal oldRatio;
    private LocalDateTime newRatio;

    // getters/setters...
}
