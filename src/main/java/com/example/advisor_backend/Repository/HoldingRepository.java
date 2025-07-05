package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.Holding;
import com.example.advisor_backend.bean.ProfitChart;
import com.example.advisor_backend.bean.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HoldingRepository extends JpaRepository<Strategy, Integer> {
    @Query("SELECT h FROM Holding h WHERE h.strategyId = :strategyId")
    List<Holding> findByStrategyId(@Param("strategyId") Integer strategyId);
}
