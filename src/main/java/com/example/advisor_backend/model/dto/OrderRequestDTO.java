package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class OrderRequestDTO {
    private Long accountId;      // 账户ID
    private Long fundId;         // 基金ID
    private String action;       // 订单操作：买入（buy）/卖出（sell）
    private Double amount;       // 金额
    private Long strategyId;     // 策略ID
    private String type;         // 订单类型（例如：错误订单、正常订单）
    private int offset;          // 分页偏移量
    private int limit;           // 分页大小
    private List<OrderDetailDTO> details;


    // getters/setters...
}