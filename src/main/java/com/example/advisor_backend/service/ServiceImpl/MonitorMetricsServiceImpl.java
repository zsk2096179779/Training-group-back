package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.repository.MonitorMetricsRepository;
import com.example.advisor_backend.model.entity.MonitorMetrics;
import com.example.advisor_backend.service.MonitorMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonitorMetricsServiceImpl implements MonitorMetricsService {
    @Autowired
    private MonitorMetricsRepository monitorMetricsRepository;
    @Override
    public MonitorMetrics getMonitoeMetricsById(Integer id) {
        // 根据ID查询关联策略
        return monitorMetricsRepository.findByStrategyId(id);
    }
}
