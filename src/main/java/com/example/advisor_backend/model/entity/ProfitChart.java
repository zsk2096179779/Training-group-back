package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
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

    @Override
    public String toString() {
        return "ProfitChart{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", dataPoints=" + dataPoints +
                '}';
    }
}


