package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.DetailRepository;
import com.example.advisor_backend.Repository.ProfitChartRepository;
import com.example.advisor_backend.bean.Detail;
import com.example.advisor_backend.bean.ProfitChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfitChartServe {
    @Autowired
    private ProfitChartRepository profitChartRepository;
    public ProfitChart getChartById(Integer id) {
        // 根据用户ID查询关联策略
        return profitChartRepository.findByStrategyId(id);
    }
}
