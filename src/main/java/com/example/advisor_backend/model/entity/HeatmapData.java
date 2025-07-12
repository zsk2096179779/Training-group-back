package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "heatmap_data")
@Data
public class HeatmapData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id", nullable = false)
    private Integer strategyId;

    @Column(name = "industry", nullable = false, length = 50)
    private String industry;

    @Column(name = "comparison_dimension", nullable = false, length = 50)
    private String comparisonDimension;

    @Column(name = "deviation_value", precision = 6, scale = 3)
    private BigDecimal deviationValue;
}
