package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.entity.FundFactor;

import java.util.List;

public interface FundFactorService {
    List<FundFactor> getFactorsByNode(Long nodeId);
    List<FundFactor> getAllFactors();
    FundFactor getFactorById(Long id);
    boolean createFactor(FundFactor factor);
    boolean updateFactor(FundFactor factor);
    boolean deleteFactor(Long id);
    public List<FundFactor> getFactorsByNodeRecursive(Long nodeId);
}
