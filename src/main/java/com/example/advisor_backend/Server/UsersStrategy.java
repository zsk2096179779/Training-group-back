package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.StrategyRepository;
import com.example.advisor_backend.bean.Strategy;
import com.example.advisor_backend.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
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
}
