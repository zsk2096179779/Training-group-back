package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "monitor_metrics")
@Data
public class MonitorMetrics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id", nullable = false)
    private Integer strategyId;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "system_status", nullable = false)
    private SystemStatus systemStatus;

    @Column(name = "uptime_days", nullable = false)
    private Integer uptimeDays;

    @Column(name = "net_value", nullable = false, precision = 10, scale = 4)
    private BigDecimal netValue;

    @Column(name = "yesterday_net_value", precision = 10, scale = 4)
    private BigDecimal yesterdayNetValue;

    @Column(name = "today_profit", precision = 16, scale = 2)
    private BigDecimal todayProfit;

    @Column(name = "today_profit_pct", precision = 6, scale = 3)
    private BigDecimal todayProfitPercentage;

    @Column(name = "ytd_profit", precision = 16, scale = 2)
    private BigDecimal yearToDateProfit;

    @Column(name = "ytd_profit_pct", precision = 6, scale = 3)
    private BigDecimal yearToDatePercentage;

    @Column(name = "avg_deviation", precision = 6, scale = 3)
    private BigDecimal averageDeviation;

    @Column(name = "max_deviation", precision = 6, scale = 3)
    private BigDecimal maxDeviation;

    @Column(name = "industry_avg_deviation", precision = 6, scale = 3)
    private BigDecimal industryAvgDeviation;

    public enum SystemStatus {
        RUNNING, STOPPED, ERROR
    }

    public MonitorMetrics() {
    }

    public MonitorMetrics(Integer id, Integer strategyId, LocalDateTime timestamp, SystemStatus systemStatus, Integer uptimeDays, BigDecimal netValue, BigDecimal yesterdayNetValue, BigDecimal todayProfit, BigDecimal todayProfitPercentage, BigDecimal yearToDateProfit, BigDecimal yearToDatePercentage, BigDecimal averageDeviation, BigDecimal maxDeviation, BigDecimal industryAvgDeviation) {
        this.id = id;
        this.strategyId = strategyId;
        this.timestamp = timestamp;
        this.systemStatus = systemStatus;
        this.uptimeDays = uptimeDays;
        this.netValue = netValue;
        this.yesterdayNetValue = yesterdayNetValue;
        this.todayProfit = todayProfit;
        this.todayProfitPercentage = todayProfitPercentage;
        this.yearToDateProfit = yearToDateProfit;
        this.yearToDatePercentage = yearToDatePercentage;
        this.averageDeviation = averageDeviation;
        this.maxDeviation = maxDeviation;
        this.industryAvgDeviation = industryAvgDeviation;
    }

    @Override
    public String toString() {
        return "MonitorMetrics{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", timestamp=" + timestamp +
                ", systemStatus=" + systemStatus +
                ", uptimeDays=" + uptimeDays +
                ", netValue=" + netValue +
                ", yesterdayNetValue=" + yesterdayNetValue +
                ", todayProfit=" + todayProfit +
                ", todayProfitPercentage=" + todayProfitPercentage +
                ", yearToDateProfit=" + yearToDateProfit +
                ", yearToDatePercentage=" + yearToDatePercentage +
                ", averageDeviation=" + averageDeviation +
                ", maxDeviation=" + maxDeviation +
                ", industryAvgDeviation=" + industryAvgDeviation +
                '}';
    }
}