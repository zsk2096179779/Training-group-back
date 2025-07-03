package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.MonitorMetrics;
import com.example.advisor_backend.bean.ProfitChart;
import com.example.advisor_backend.bean.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitorMetricsRepository extends JpaRepository<MonitorMetrics, Integer> {
    MonitorMetrics findByStrategyId(Integer strategyId);
}
