package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "holdings")
@Data
public class StrategyHolding {
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

    public StrategyHolding() {
    }

    public StrategyHolding(Integer id, Integer strategyId, Stock stock, Double weight, BigDecimal marketValue, BigDecimal cost, Double profitPercentage, Integer holdingDays) {
        this.id = id;
        this.strategyId = strategyId;
        this.stock = stock;
        this.weight = weight;
        this.marketValue = marketValue;
        this.cost = cost;
        this.profitPercentage = profitPercentage;
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
