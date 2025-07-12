package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Setter
@Getter
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
