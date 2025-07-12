package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.MonitorMetrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorMetricsRepository extends JpaRepository<MonitorMetrics, Integer> {
    MonitorMetrics findByStrategyId(Integer strategyId);
}
