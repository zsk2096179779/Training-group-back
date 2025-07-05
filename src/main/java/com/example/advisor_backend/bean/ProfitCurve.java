package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "profit_curve")
@Data
public class ProfitCurve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id", nullable = false)
    private Integer strategyId;

    @Column(name = "point_date", nullable = false)
    private LocalDate pointDate;

    @Column(name = "net_value", nullable = false, precision = 10, scale = 4)
    private BigDecimal netValue;

    @Column(name = "return_rate", precision = 8, scale = 4)
    private BigDecimal returnRate;

    public ProfitCurve() {
    }

    public ProfitCurve(Integer id, Integer strategyId, LocalDate pointDate, BigDecimal netValue, BigDecimal returnRate) {
        this.id = id;
        this.strategyId = strategyId;
        this.pointDate = pointDate;
        this.netValue = netValue;
        this.returnRate = returnRate;
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

    public LocalDate getPointDate() {
        return pointDate;
    }

    public void setPointDate(LocalDate pointDate) {
        this.pointDate = pointDate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(BigDecimal returnRate) {
        this.returnRate = returnRate;
    }

    @Override
    public String toString() {
        return "ProfitCurve{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", pointDate=" + pointDate +
                ", netValue=" + netValue +
                ", returnRate=" + returnRate +
                '}';
    }
}

