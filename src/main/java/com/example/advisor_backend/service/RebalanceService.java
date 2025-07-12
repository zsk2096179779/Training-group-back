package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.BacktestRequest;
import com.example.advisor_backend.model.dto.BacktestResponse;
import com.example.advisor_backend.model.entity.RebalanceSetting;

public interface RebalanceService {
    RebalanceSetting getRebalanceById(Integer id);
    void save(RebalanceSetting setting);
    BacktestResponse runBacktest(BacktestRequest req);
}
