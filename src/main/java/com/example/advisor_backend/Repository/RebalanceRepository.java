package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.ProfitCurve;
import com.example.advisor_backend.bean.RebalanceSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebalanceRepository extends JpaRepository<RebalanceSetting, Integer> {
    RebalanceSetting findByStrategyId(Integer strategyId);
}
