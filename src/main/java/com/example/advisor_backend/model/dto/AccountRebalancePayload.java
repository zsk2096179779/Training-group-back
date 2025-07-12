package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AccountRebalancePayload {
    private Long accountId;
    private List<FundRebalance> funds;

    @Getter @Setter
    public static class FundRebalance {
        private Long fundId;
        private Double targetRatio;

        // Getters and Setters
    }

    // Getters and Setters
}

