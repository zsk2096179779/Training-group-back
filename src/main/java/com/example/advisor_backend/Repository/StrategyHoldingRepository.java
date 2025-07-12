package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.StrategyHolding;
import com.example.advisor_backend.model.entity.ProfitChart;
import com.example.advisor_backend.model.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StrategyHoldingRepository extends JpaRepository<Strategy, Integer> {
    @Query("SELECT h FROM StrategyHolding h WHERE h.strategyId = :strategyId")
    List<StrategyHolding> findByStrategyId(@Param("strategyId") Integer strategyId);
}
