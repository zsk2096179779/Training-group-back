package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Entity
@Table(name = "rebalance")
@Data
public class RebalanceSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 调仓类型：true=主动调仓, false=被动调仓
    private boolean activeRebalancing;

    // 触发条件：阈值触发
    private boolean triggerByThreshold;

    // 触发条件：定期触发
    private boolean triggerByPeriodic;

    // 再平衡频率
    private String frequency = ""; // 默认空字符串

    // 调仓执行时间
    private LocalTime executionTime;

    // 最大调仓比例 (百分比)
    private int maxAdjustmentRate = 0;

    // 资产偏离度阈值 (所有阈值默认0)
    private int stockDeviation = 0;
    private int bondDeviation = 0;
    private int commodityDeviation = 0;
    private int cashDeviation = 0;

    private Integer strategyId;

    public RebalanceSetting() {
    }

    public RebalanceSetting(Long id, boolean activeRebalancing, boolean triggerByThreshold, boolean triggerByPeriodic, String frequency, LocalTime executionTime, int maxAdjustmentRate, int stockDeviation, int bondDeviation, int commodityDeviation, int cashDeviation, Integer strategyId) {
        this.id = id;
        this.activeRebalancing = activeRebalancing;
        this.triggerByThreshold = triggerByThreshold;
        this.triggerByPeriodic = triggerByPeriodic;
        this.frequency = frequency;
        this.executionTime = executionTime;
        this.maxAdjustmentRate = maxAdjustmentRate;
        this.stockDeviation = stockDeviation;
        this.bondDeviation = bondDeviation;
        this.commodityDeviation = commodityDeviation;
        this.cashDeviation = cashDeviation;
        this.strategyId = strategyId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActiveRebalancing() {
        return activeRebalancing;
    }

    public void setActiveRebalancing(boolean activeRebalancing) {
        this.activeRebalancing = activeRebalancing;
    }

    public boolean isTriggerByThreshold() {
        return triggerByThreshold;
    }

    public void setTriggerByThreshold(boolean triggerByThreshold) {
        this.triggerByThreshold = triggerByThreshold;
    }

    public boolean isTriggerByPeriodic() {
        return triggerByPeriodic;
    }

    public void setTriggerByPeriodic(boolean triggerByPeriodic) {
        this.triggerByPeriodic = triggerByPeriodic;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public LocalTime getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(LocalTime executionTime) {
        this.executionTime = executionTime;
    }

    public int getMaxAdjustmentRate() {
        return maxAdjustmentRate;
    }

    public void setMaxAdjustmentRate(int maxAdjustmentRate) {
        this.maxAdjustmentRate = maxAdjustmentRate;
    }

    public int getStockDeviation() {
        return stockDeviation;
    }

    public void setStockDeviation(int stockDeviation) {
        this.stockDeviation = stockDeviation;
    }

    public int getBondDeviation() {
        return bondDeviation;
    }

    public void setBondDeviation(int bondDeviation) {
        this.bondDeviation = bondDeviation;
    }

    public int getCommodityDeviation() {
        return commodityDeviation;
    }

    public void setCommodityDeviation(int commodityDeviation) {
        this.commodityDeviation = commodityDeviation;
    }

    public int getCashDeviation() {
        return cashDeviation;
    }

    public void setCashDeviation(int cashDeviation) {
        this.cashDeviation = cashDeviation;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    @Override
    public String toString() {
        return "RebalanceSetting{" +
                "id=" + id +
                ", activeRebalancing=" + activeRebalancing +
                ", triggerByThreshold=" + triggerByThreshold +
                ", triggerByPeriodic=" + triggerByPeriodic +
                ", frequency='" + frequency + '\'' +
                ", executionTime=" + executionTime +
                ", maxAdjustmentRate=" + maxAdjustmentRate +
                ", stockDeviation=" + stockDeviation +
                ", bondDeviation=" + bondDeviation +
                ", commodityDeviation=" + commodityDeviation +
                ", cashDeviation=" + cashDeviation +
                ", strategyId=" + strategyId +
                '}';
    }
}
