package com.example.advisor_backend.model.entity;


//portfolio

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Combo")
public class Combo {

    @Id
    private String id; // 主键：组合ID

    private String name;
    private String type; // 建议改为枚举类型（如FOF/CUSTOM）
    private String riskLevel;
    private String strategy;
    private String status;
    private BigDecimal annualizedReturn;
    private BigDecimal maxDrawdown;
    private Double minimumInvestment;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String source;
    private boolean isUserCreated;

    @Transient
    private List<Fund> funds; // 多对多关联基金

    public void setIsUserCreated(boolean isUserCreated) {
        this.isUserCreated = isUserCreated;
    }
}
