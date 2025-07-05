package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "holdings")
@Data
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id")
    private Integer strategyId;

    @ManyToOne
    @JoinColumn(name = "stock_code", referencedColumnName = "stock_code")
    private Stock stock;

    @Column(name = "weight")
    private Double weight;  // 持仓占比(%)

    @Column(name = "market_value")
    private BigDecimal marketValue;  // 持仓市值

    @Column(name = "cost")
    private BigDecimal cost;  // 持仓成本

    @Column(name = "profit_percentage")
    private Double profitPercentage;  // 浮动盈亏百分比

    @Column(name = "holding_days")
    private Integer holdingDays;  // 持仓天数

    public Holding() {
    }

    public Holding(Integer id, Integer strategyId, Stock stock, Double weight, BigDecimal marketValue, BigDecimal cost, Double profitPercentage, Integer holdingDays) {
        this.id = id;
        this.strategyId = strategyId;
        this.stock = stock;
        this.weight = weight;
        this.marketValue = marketValue;
        this.cost = cost;
        this.profitPercentage = profitPercentage;
        this.holdingDays = holdingDays;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Double getProfitPercentage() {
        return profitPercentage;
    }

    public void setProfitPercentage(Double profitPercentage) {
        this.profitPercentage = profitPercentage;
    }

    public Integer getHoldingDays() {
        return holdingDays;
    }

    public void setHoldingDays(Integer holdingDays) {
        this.holdingDays = holdingDays;
    }

    @Override
    public String toString() {
        return "Holding{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", stock=" + stock +
                ", weight=" + weight +
                ", marketValue=" + marketValue +
                ", cost=" + cost +
                ", profitPercentage=" + profitPercentage +
                ", holdingDays=" + holdingDays +
                '}';
    }
}
