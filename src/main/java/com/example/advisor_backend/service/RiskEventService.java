package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.RiskEvent;

import java.util.List;

public interface RiskEventService {
    List<RiskEvent> getRiskEventById(Integer id);
}
