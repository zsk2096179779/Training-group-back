package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profit_chart")
@Data
public class ProfitChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id")
    private Integer strategyId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "chart_id")
    private List<ChartDataPoint> dataPoints = new ArrayList<>();

    public ProfitChart() {
    }

    public ProfitChart(Integer id, Integer strategyId, List<ChartDataPoint> dataPoints) {
        this.id = id;
        this.strategyId = strategyId;
        this.dataPoints = dataPoints;
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

    public List<ChartDataPoint> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<ChartDataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    @Override
    public String toString() {
        return "ProfitChart{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", dataPoints=" + dataPoints +
                '}';
    }
}


