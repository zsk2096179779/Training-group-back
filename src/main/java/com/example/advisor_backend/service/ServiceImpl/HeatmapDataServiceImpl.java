package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.Repository.HeatmapDataRepository;
import com.example.advisor_backend.model.entity.HeatmapData;
import com.example.advisor_backend.service.HeatmapDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HeatmapDataServiceImpl implements HeatmapDataService {
    @Autowired
    private HeatmapDataRepository heatmapDataRepository;
    @Override
    public List<HeatmapData> getHeatmapDataById(Integer id) {
        // 根据ID查询关联策略
        return heatmapDataRepository.findByStrategyId(id);
    }
}
