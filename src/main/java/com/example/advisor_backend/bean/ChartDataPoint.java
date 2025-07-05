package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

// 新创建的嵌入式实体
@Entity
@Table(name = "chart_data_points")
@Data
public class ChartDataPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "date_label", nullable = false)
    private String date;

    @Column(name = "strategy_return", nullable = false)
    private double strategyReturn;

    @Column(name = "benchmark_return", nullable = false)
    private double benchmarkReturn;

    public ChartDataPoint() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getStrategyReturn() {
        return strategyReturn;
    }

    public void setStrategyReturn(double strategyReturn) {
        this.strategyReturn = strategyReturn;
    }

    public double getBenchmarkReturn() {
        return benchmarkReturn;
    }

    public void setBenchmarkReturn(double benchmarkReturn) {
        this.benchmarkReturn = benchmarkReturn;
    }

    @Override
    public String toString() {
        return "ChartDataPoint{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", strategyReturn=" + strategyReturn +
                ", benchmarkReturn=" + benchmarkReturn +
                '}';
    }
}
