package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.model.dto.BacktestRequest;
import com.example.advisor_backend.model.dto.BacktestResponse;
import com.example.advisor_backend.Repository.RebalanceRepository;
import com.example.advisor_backend.model.entity.RebalanceSetting;
import com.example.advisor_backend.service.ProfitChartService;
import com.example.advisor_backend.service.RebalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RebalanceServiceImpl implements RebalanceService {
    @Autowired
    private RebalanceRepository rebalanceRepository;

    @Override
    public RebalanceSetting getRebalanceById(Integer id)
    {
        return rebalanceRepository.findByStrategyId(id);
    }
    @Override
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

    public BacktestResponse runBacktest(BacktestRequest request) {
        // 这里可以调用真实的回测引擎；示例仍用随机数：
        BacktestResponse resp = new BacktestResponse();
        resp.setCumulativeReturn(Math.random() * 2 - 0.5);
        resp.setMaxDrawdown(Math.random() * 0.5);
        resp.setSharpeRatio(Math.random() * 3);
        resp.setTrades((int) (Math.random() * 100));
        return resp;

        }
    }

