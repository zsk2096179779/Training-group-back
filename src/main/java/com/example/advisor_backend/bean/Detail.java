package com.example.advisor_backend.bean;

import jakarta.persistence.*;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Double getTotalReturn() {
        return totalReturn;
    }

    public void setTotalReturn(Double totalReturn) {
        this.totalReturn = totalReturn;
    }

    public Double getAnnualReturn() {
        return annualReturn;
    }

    public void setAnnualReturn(Double annualReturn) {
        this.annualReturn = annualReturn;
    }

    public Double getMaxDrawdown() {
        return maxDrawdown;
    }

    public void setMaxDrawdown(Double maxDrawdown) {
        this.maxDrawdown = maxDrawdown;
    }

    public Double getRecent30dReturn() {
        return recent30dReturn;
    }

    public void setRecent30dReturn(Double recent30dReturn) {
        this.recent30dReturn = recent30dReturn;
    }

    public Double getRecent6mReturn() {
        return recent6mReturn;
    }

    public void setRecent6mReturn(Double recent6mReturn) {
        this.recent6mReturn = recent6mReturn;
    }

    public Double getExcessReturn() {
        return excessReturn;
    }

    public void setExcessReturn(Double excessReturn) {
        this.excessReturn = excessReturn;
    }

    public Double getSharpeRatio() {
        return sharpeRatio;
    }

    public void setSharpeRatio(Double sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    public Double getBeta() {
        return beta;
    }

    public void setBeta(Double beta) {
        this.beta = beta;
    }

    public Double getInformationRatio() {
        return informationRatio;
    }

    public void setInformationRatio(Double informationRatio) {
        this.informationRatio = informationRatio;
    }

    public Integer getWinRateCount() {
        return winRateCount;
    }

    public void setWinRateCount(Integer winRateCount) {
        this.winRateCount = winRateCount;
    }

    public Double getWinRatePercentage() {
        return winRatePercentage;
    }

    public void setWinRatePercentage(Double winRatePercentage) {
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