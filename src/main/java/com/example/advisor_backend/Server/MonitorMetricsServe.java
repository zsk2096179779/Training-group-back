package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.HoldingRepository;
import com.example.advisor_backend.Repository.MonitorMetricsRepository;
import com.example.advisor_backend.bean.Holding;
import com.example.advisor_backend.bean.MonitorMetrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MonitorMetricsServe {
    @Autowired
    private MonitorMetricsRepository monitorMetricsRepository;
    public MonitorMetrics getMonitoeMetricsById(Integer id) {
        // 根据ID查询关联策略
        return monitorMetricsRepository.findByStrategyId(id);
    }
}
