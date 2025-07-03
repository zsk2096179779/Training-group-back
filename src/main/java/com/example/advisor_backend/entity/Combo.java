package com.example.advisor_backend.entity;


//portfolio

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Combo {
    private String isUserCreated;
    private String id; // 主键：组合ID
    private String name;
    private String type; // 建议改为枚举类型（如FOF/CUSTOM）
    private String riskLevel;
    private String strategy;
    private String status;
    private BigDecimal annualizedReturn;
    private BigDecimal maxDrawdown;
    private Integer minimumInvestment;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private List<Fund> funds; // 多对多关联基金
}
