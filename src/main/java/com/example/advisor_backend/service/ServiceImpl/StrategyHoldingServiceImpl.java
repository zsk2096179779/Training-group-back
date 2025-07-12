package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.model.entity.StrategyHolding;
import com.example.advisor_backend.Repository.StrategyHoldingRepository;
import com.example.advisor_backend.service.StrategyHoldingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyHoldingServiceImpl implements StrategyHoldingService {
    @Autowired
    private StrategyHoldingRepository strategyHoldingRepository;

    @Override
    public List<StrategyHolding> getHoldingById(Integer id) {
        // 根据ID查询关联策略
        return strategyHoldingRepository.findByStrategyId(id);
    }
}
