package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.ProfitChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitChartRepository extends JpaRepository<ProfitChart, Integer> {
    ProfitChart findByStrategyId(Integer strategyId);
}
