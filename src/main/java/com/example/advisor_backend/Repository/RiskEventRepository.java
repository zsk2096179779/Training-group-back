package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.RiskEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RiskEventRepository extends JpaRepository<RiskEvent, Integer> {
    List<RiskEvent> findByStrategyId(Integer strategyId);
}
