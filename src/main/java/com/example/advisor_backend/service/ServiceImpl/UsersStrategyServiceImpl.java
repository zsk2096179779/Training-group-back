package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.mapper.StrategyMapper;
import com.example.advisor_backend.repository.StrategyRepository;
import com.example.advisor_backend.model.entity.Strategy;
import com.example.advisor_backend.exception.BusinessException;
import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.service.UsersStrategyService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersStrategyServiceImpl implements UsersStrategyService {
    @Autowired
    private StrategyRepository strategyRepository;

    @Autowired
    private StrategyMapper strategyMapper;

    @Override
    public List<Strategy> getAllStrategies() {
        // 直接返回所有策略数据
        return strategyRepository.findAll();
    }

    @Override
    public Strategy getStrategiesById(Integer strategyId) {
        // 根据用户ID查询关联策略
        return strategyRepository.findByid(strategyId);
    }

    @Override
    public Page<Strategy> getStrategiesByUserAndFilter(
            User user,
            int page,
            int limit,
            String nameFilter,
            String statusFilter
    ) {
        // 计算分页的偏移量
        int offset = page * limit;

        // 查询策略数据
        List<Strategy> strategies = strategyMapper.findAll(
                user.getId(),
                nameFilter,
                statusFilter,
                offset,
                limit
        );

        // 打印查询到的策略数据
        for (Strategy strategy : strategies) {
            System.out.println("Found Strategy: " + strategy.getName());
        }

// 查询策略总数
        long total = strategyMapper.countStrategies(user.getId(), nameFilter, statusFilter);
        System.out.println("Total strategies: " + total);

        // 使用 Spring Data JPA 的 PageImpl 创建分页结果
        return new PageImpl<>(strategies, PageRequest.of(page, limit), total);

    }


    @Transactional
    public void startStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        strategy.setStatus(Strategy.Status.valueOf("active"));
        strategyRepository.save(strategy);
    }

    @Transactional
    public void stopStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        strategy.setStatus(Strategy.Status.valueOf("stop"));
        strategyRepository.save(strategy);
    }

    @Transactional
    public void deleteStrategy(Integer strategyId) {
        Strategy strategy = getStrategiesById(strategyId);
        // 先停止策略再删除
        strategyRepository.deleteById(strategyId);
    }
    @Transactional
    public Strategy createStrategy(User request) {
        Strategy strategy = new Strategy(request.getUsername(), Math.toIntExact(request.getId()));
        return strategyRepository.save(strategy);
    }
}
