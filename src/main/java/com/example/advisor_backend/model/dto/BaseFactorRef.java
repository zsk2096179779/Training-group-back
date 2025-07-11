package com.example.advisor_backend.model.dto;

public class BaseFactorRef {
    private Long baseFactorId;
    private Double weight;

    // getter/setter
    public Long getBaseFactorId() { return baseFactorId; }
    public void setBaseFactorId(Long baseFactorId) { this.baseFactorId = baseFactorId; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}