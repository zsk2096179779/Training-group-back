package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.Strategy;
import com.example.advisor_backend.model.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UsersStrategyService {
    List<Strategy> getAllStrategies();

    Strategy getStrategiesById(Integer strategyId);

    Page<Strategy> getStrategiesByUserAndFilter(User user, int page, int limit, String nameFilter, String statusFilter);
}
