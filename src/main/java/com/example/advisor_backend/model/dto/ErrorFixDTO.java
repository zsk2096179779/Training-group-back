package com.example.advisor_backend.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ErrorFixDTO {
    private Long orderId;     // 错误订单ID
    private Long newFundId;   // 新基金ID
    private String fixReason; // 修复原因

}