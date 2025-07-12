package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.TradeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Integer> {
    @Query("SELECT t FROM TradeHistory t WHERE t.strategyId = :strategyId")
    List<TradeHistory> findByStrategyId(@Param("strategyId") Integer strategyId);
}
