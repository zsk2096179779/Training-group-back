package com.example.advisor_backend.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorOrderDTO {
    private Long orderId;           // 错误订单ID
    private String errorReason;     // 错误原因
    private String originalFundId; // 原始基金ID
    private String newFundId;      // 新基金ID
    private boolean resolved;      // 是否已解决
}
