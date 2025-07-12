package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
@Table(name = "details")
public class Detail {
    @Id
    private Integer id;
    @OneToOne
    @MapsId
    @JoinColumn(name = "strategy_id", referencedColumnName = "id")
    private Strategy strategy;
    // 核心收益指标
    @Column(name = "total_return")
    private Double totalReturn;
    @Column(name = "annual_return")
    private Double annualReturn;
    @Column(name = "max_drawdown")
    private Double maxDrawdown;
    // 补充指标
    @Column(name = "recent_30d")
    private Double recent30dReturn;
    @Column(name = "recent_6m")
    private Double recent6mReturn;
    @Column(name = "excess_return")
    private Double excessReturn;
    // 核心指标
    @Column(name = "sharpe_ratio")
    private Double sharpeRatio;
    @Column(name = "volatility")
    private Double volatility;
    @Column(name = "beta")
    private Double beta;
    @Column(name = "information_ratio")
    private Double informationRatio;
    @Column(name = "win_rate_count")
    private Integer winRateCount;
    @Column(name = "win_rate_percentage")
    private Double winRatePercentage;
    // 构造器

    public Detail() {
    }

    public Detail(Integer id, Strategy strategy, Double totalReturn, Double annualReturn, Double maxDrawdown, Double recent30dReturn, Double recent6mReturn, Double excessReturn, Double sharpeRatio, Double volatility, Double beta, Double informationRatio, Integer winRateCount, Double winRatePercentage) {
        this.id = id;
        this.strategy = strategy;
        this.totalReturn = totalReturn;
        this.annualReturn = annualReturn;
        this.maxDrawdown = maxDrawdown;
        this.recent30dReturn = recent30dReturn;
        this.recent6mReturn = recent6mReturn;
        this.excessReturn = excessReturn;
        this.sharpeRatio = sharpeRatio;
        this.volatility = volatility;
        this.beta = beta;
        this.informationRatio = informationRatio;
        this.winRateCount = winRateCount;
        this.winRatePercentage = winRatePercentage;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "id=" + id +
                ", strategy=" + strategy +
                ", totalReturn=" + totalReturn +
                ", annualReturn=" + annualReturn +
                ", maxDrawdown=" + maxDrawdown +
                ", recent30dReturn=" + recent30dReturn +
                ", recent6mReturn=" + recent6mReturn +
                ", excessReturn=" + excessReturn +
                ", sharpeRatio=" + sharpeRatio +
                ", volatility=" + volatility +
                ", beta=" + beta +
                ", informationRatio=" + informationRatio +
                ", winRateCount=" + winRateCount +
                ", winRatePercentage=" + winRatePercentage +
                '}';
    }
}