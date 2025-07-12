package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.StrategyHolding;

import java.util.List;

public interface StrategyHoldingService {
    List<StrategyHolding> getHoldingById(Integer id);
}
