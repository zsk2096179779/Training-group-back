package com.example.advisor_backend.model.entity;

public class FactorDerivation {
    private Long id; // 主键
    private Long factorId; // 衍生因子ID
    private Long baseFactorId; // 基础因子ID
    private java.math.BigDecimal weight; // 权重
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getFactorId() { return factorId; }
    public void setFactorId(Long factorId) { this.factorId = factorId; }
    public Long getBaseFactorId() { return baseFactorId; }
    public void setBaseFactorId(Long baseFactorId) { this.baseFactorId = baseFactorId; }
    public java.math.BigDecimal getWeight() { return weight; }
    public void setWeight(java.math.BigDecimal weight) { this.weight = weight; }
} 