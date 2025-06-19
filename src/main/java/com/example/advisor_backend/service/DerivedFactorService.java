package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.CreateDerivedFactorRequest;

public interface DerivedFactorService {
    Long createDerivedFactor(CreateDerivedFactorRequest request);
} 