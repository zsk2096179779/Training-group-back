package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.MonitorMetricsRepository;
import com.example.advisor_backend.Repository.RiskEventRepository;
import com.example.advisor_backend.bean.MonitorMetrics;
import com.example.advisor_backend.bean.RiskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEventServe {
    @Autowired
    private RiskEventRepository riskEventRepository;
    public List<RiskEvent> getRiskEventById(Integer id) {
        // 根据ID查询关联策略
        return riskEventRepository.findByStrategyId(id);
    }
}
