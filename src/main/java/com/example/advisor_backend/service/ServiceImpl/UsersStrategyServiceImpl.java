package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.Repository.StrategyRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public List<Strategy> getStrategiesByUser(User user) {
        // 根据用户ID查询关联策略
        return strategyRepository.findByOwner(Math.toIntExact(user.getId()));
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
        // 注意 page 前端传 1 就对应 Spring Data 的 0
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createTime"));

        Specification<Strategy> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // 1) 按 owner 字段过滤
            predicates.add(cb.equal(root.get("owner"), user.getId()));

            // 2) 名称搜索
            if (StringUtils.hasText(nameFilter)) {
                predicates.add(cb.like(root.get("name"), "%" + nameFilter.trim() + "%"));
            }

            // 3) 状态过滤（all 表示不过滤）
            if (StringUtils.hasText(statusFilter) && !"all".equalsIgnoreCase(statusFilter)) {
                // 如果你的实体里 status 字段是枚举或者大写存储，请做相应调整
                predicates.add(cb.equal(root.get("status"), statusFilter.toUpperCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return strategyRepository.findAll(spec, pageable);
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
        Strategy strategy = new Strategy(request.getUsername(), Math.toIntExact(request.getId()));
        return strategyRepository.save(strategy);
    }
}
