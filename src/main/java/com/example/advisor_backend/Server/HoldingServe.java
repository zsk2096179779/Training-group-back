package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.HoldingRepository;
import com.example.advisor_backend.Repository.ProfitChartRepository;
import com.example.advisor_backend.bean.Holding;
import com.example.advisor_backend.bean.ProfitChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HoldingServe {
    @Autowired
    private HoldingRepository holdingRepository;
    public List<Holding> getHoldingById(Integer id) {
        // 根据ID查询关联策略
        return holdingRepository.findByStrategyId(id);
    }
}
