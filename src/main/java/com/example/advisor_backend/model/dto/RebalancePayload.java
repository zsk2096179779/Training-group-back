package com.example.advisor_backend.model.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebalancePayload {
    private Long strategyId;  // 策略ID
    private List<RebalanceDetailDTO> details;  // 调仓细节
    private List<FundRebalance> funds;
    private Long accountId;

    @Data
    @Getter @Setter
    public static class FundRebalance {
        private Long fundId;         // 基金ID
        private Double currentRatio; // 当前比例
        private Double targetRatio;  // 目标比例
    }
}

