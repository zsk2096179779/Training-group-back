package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.Detail;
import com.example.advisor_backend.model.entity.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailRepository extends JpaRepository<Strategy, Integer> {
    //通过id查询
    @Query("SELECT d FROM Detail d WHERE d.strategy.id = :strategyId")
    Detail findByStrategyId(@Param("strategyId") Integer strategyId);
}
