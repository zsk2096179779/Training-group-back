package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.HeatmapDataRepository;
import com.example.advisor_backend.Repository.MonitorMetricsRepository;
import com.example.advisor_backend.bean.HeatmapData;
import com.example.advisor_backend.bean.MonitorMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeatmapDataServe {
    @Autowired
    private HeatmapDataRepository heatmapDataRepository;
    public List<HeatmapData> getHeatmapDataById(Integer id) {
        // 根据ID查询关联策略
        return heatmapDataRepository.findByStrategyId(id);
    }
}
