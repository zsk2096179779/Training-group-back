package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.Repository.RiskEventRepository;
import com.example.advisor_backend.model.entity.RiskEvent;
import com.example.advisor_backend.service.ProfitCurveService;
import com.example.advisor_backend.service.RiskEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiskEventServiceImpl implements RiskEventService {
    @Autowired
    private RiskEventRepository riskEventRepository;
    @Override
    public List<RiskEvent> getRiskEventById(Integer id) {
        // 根据ID查询关联策略
        return riskEventRepository.findByStrategyId(id);
    }
}
