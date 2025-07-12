package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.HeatmapData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeatmapDataRepository extends JpaRepository<HeatmapData, Integer> {
    List<HeatmapData> findByStrategyId(Integer strategyId);
}
