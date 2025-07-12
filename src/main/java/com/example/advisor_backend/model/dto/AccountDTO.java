package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private Long accountId;
    private String accountName;
    private Double deviation;

    public AccountDTO(Long accountId, String accountName) {
        this.accountId = accountId;
        this.accountName = accountName;
    }
    // Getters and Setters
}

