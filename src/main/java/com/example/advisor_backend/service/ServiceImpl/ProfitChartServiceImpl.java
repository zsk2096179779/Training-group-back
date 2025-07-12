package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.Repository.ProfitChartRepository;
import com.example.advisor_backend.model.entity.ProfitChart;
import com.example.advisor_backend.service.ProfitChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfitChartServiceImpl implements ProfitChartService {
    @Autowired
    private ProfitChartRepository profitChartRepository;
    @Override
    public ProfitChart getChartById(Integer id) {
        // 根据用户ID查询关联策略
        return profitChartRepository.findByStrategyId(id);
    }
}
