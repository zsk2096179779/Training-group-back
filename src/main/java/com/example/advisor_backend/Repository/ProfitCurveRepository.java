package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.ProfitCurve;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfitCurveRepository extends JpaRepository<ProfitCurve, Integer> {
    List<ProfitCurve> findByStrategyId(Integer strategyId);
}
