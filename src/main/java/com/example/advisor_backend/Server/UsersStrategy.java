package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.StrategyRepository;
import com.example.advisor_backend.bean.Strategy;
import com.example.advisor_backend.bean.User;
import com.example.advisor_backend.exception.BusinessException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersStrategy {
    @Autowired
    private StrategyRepository strategyRepository;

    public List<Strategy> getStrategiesByUser(User user) {
        // 根据用户ID查询关联策略
        return strategyRepository.findByOwner(user.getId());
    }

    public Strategy getStrategiesById(Integer strategyId) {
        // 根据用户ID查询关联策略
        return strategyRepository.findByid(strategyId);
    }

    @Transactional
    public void startStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        if ("active".equals(strategy.getStatus())) {
            throw new BusinessException(400, "策略已运行", HttpStatus.BAD_REQUEST);
        }
        strategy.setStatus(Strategy.Status.valueOf("active"));
        strategyRepository.save(strategy);
    }

    @Transactional
    public void stopStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        if ("stop".equals(strategy.getStatus())) {
            throw new BusinessException(400, "策略已停止", HttpStatus.BAD_REQUEST);
        }
        strategy.setStatus(Strategy.Status.valueOf("stop"));
        strategyRepository.save(strategy);
    }

    @Transactional
    public void deleteStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        // 先停止策略再删除
        if ("active".equals(strategy.getStatus())) {
            stopStrategy(strategyId);
        }
        strategyRepository.deleteById(strategyId);
    }
    @Transactional
    public Strategy createStrategy(User request) {
        Strategy strategy = new Strategy(request.getName(), request.getId());
        return strategyRepository.save(strategy);
    }
}
