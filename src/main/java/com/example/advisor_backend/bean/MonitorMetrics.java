package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public SystemStatus getSystemStatus() {
        return systemStatus;
    }

    public void setSystemStatus(SystemStatus systemStatus) {
        this.systemStatus = systemStatus;
    }

    public Integer getUptimeDays() {
        return uptimeDays;
    }

    public void setUptimeDays(Integer uptimeDays) {
        this.uptimeDays = uptimeDays;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getYesterdayNetValue() {
        return yesterdayNetValue;
    }

    public void setYesterdayNetValue(BigDecimal yesterdayNetValue) {
        this.yesterdayNetValue = yesterdayNetValue;
    }

    public BigDecimal getTodayProfit() {
        return todayProfit;
    }

    public void setTodayProfit(BigDecimal todayProfit) {
        this.todayProfit = todayProfit;
    }

    public BigDecimal getTodayProfitPercentage() {
        return todayProfitPercentage;
    }

    public void setTodayProfitPercentage(BigDecimal todayProfitPercentage) {
        this.todayProfitPercentage = todayProfitPercentage;
    }

    public BigDecimal getYearToDateProfit() {
        return yearToDateProfit;
    }

    public void setYearToDateProfit(BigDecimal yearToDateProfit) {
        this.yearToDateProfit = yearToDateProfit;
    }

    public BigDecimal getYearToDatePercentage() {
        return yearToDatePercentage;
    }

    public void setYearToDatePercentage(BigDecimal yearToDatePercentage) {
        this.yearToDatePercentage = yearToDatePercentage;
    }

    public BigDecimal getAverageDeviation() {
        return averageDeviation;
    }

    public void setAverageDeviation(BigDecimal averageDeviation) {
        this.averageDeviation = averageDeviation;
    }

    public BigDecimal getMaxDeviation() {
        return maxDeviation;
    }

    public void setMaxDeviation(BigDecimal maxDeviation) {
        this.maxDeviation = maxDeviation;
    }

    public BigDecimal getIndustryAvgDeviation() {
        return industryAvgDeviation;
    }

    public void setIndustryAvgDeviation(BigDecimal industryAvgDeviation) {
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