// src/web/dto/ComboCreateRequest.java
package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ComboCreateRequest {
    private String name;
    private String type;
    private String riskLevel;
    private String strategy;
    private Double annualizedReturn;
    private Double maxDrawdown;
    private Double minimumInvestment;
    private Boolean isUserCreated;      // 前端传 "1" 或 "true"
    private List<FundRef> funds;         // 传过来只是 code 的列表
    private String source;

    // getters & setters ...
}
