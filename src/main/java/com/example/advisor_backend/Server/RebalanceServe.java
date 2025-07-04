package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.RebalanceRepository;
import com.example.advisor_backend.bean.RebalanceSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RebalanceServe {
    @Autowired
    private RebalanceRepository rebalanceRepository;
    public RebalanceSetting getRebalanceById(Integer id)
    {
        return rebalanceRepository.findByStrategyId(id);
    }
    public void save(RebalanceSetting setting) {
        // 确保 strategyId 有值
        if (setting.getStrategyId() == null) {
            throw new IllegalArgumentException("Strategy ID cannot be null");
        }
        // 更新或创建新配置
        RebalanceSetting toSave = rebalanceRepository.findByStrategyId(setting.getStrategyId());
        // 更新字段
        toSave.setActiveRebalancing(setting.isActiveRebalancing());
        toSave.setTriggerByThreshold(setting.isTriggerByThreshold());
        toSave.setTriggerByPeriodic(setting.isTriggerByPeriodic());
        toSave.setFrequency(setting.getFrequency());
        toSave.setExecutionTime(setting.getExecutionTime());
        toSave.setMaxAdjustmentRate(setting.getMaxAdjustmentRate());
        toSave.setStockDeviation(setting.getStockDeviation());
        toSave.setBondDeviation(setting.getBondDeviation());
        toSave.setCommodityDeviation(setting.getCommodityDeviation());
        toSave.setCashDeviation(setting.getCashDeviation());

        // 保存到数据库
        rebalanceRepository.save(toSave);
    }
}
