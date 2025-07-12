package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 新创建的嵌入式实体
@Setter
@Getter
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
