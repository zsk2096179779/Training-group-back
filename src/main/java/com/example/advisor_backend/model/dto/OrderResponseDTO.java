package com.example.advisor_backend.model.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单列表项返回 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class OrderResponseDTO {
    private Long orderId;        // 订单ID
    private String status;       // 订单状态
    private Double amount;       // 金额
    private String fundName;     // 基金名称
    private String action;       // 操作类型（买入或卖出）
    private String createTime;   // 订单创建时间
    private BigDecimal quantity;
    private String fund;
    private String account;

}