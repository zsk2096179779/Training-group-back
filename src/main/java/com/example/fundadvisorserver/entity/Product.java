package com.example.fundadvisorserver.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private Double minPurchase;
    private Double yieldRate;
    private String yieldPeriod;
    private String description;
    private String tag;
    private String riskLevel;
    private Timestamp createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Double getMinPurchase() { return minPurchase; }
    public void setMinPurchase(Double minPurchase) { this.minPurchase = minPurchase; }
    public Double getYieldRate() { return yieldRate; }
    public void setYieldRate(Double yieldRate) { this.yieldRate = yieldRate; }
    public String getYieldPeriod() { return yieldPeriod; }
    public void setYieldPeriod(String yieldPeriod) { this.yieldPeriod = yieldPeriod; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
} 