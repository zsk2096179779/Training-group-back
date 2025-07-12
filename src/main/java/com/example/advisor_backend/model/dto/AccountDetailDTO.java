package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AccountDetailDTO {
    private Long accountId;
    private List<FundDetail> funds;

    public static class FundDetail {
        private Long fundId;
        private String fundName;
        private Double currentRatio;
        private Double targetRatio;

        // Getters and Setters
    }

    // Getters and Setters
}
