package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.DerivedFactorRelationMapper;
import com.example.advisor_backend.model.dto.CreateDerivedFactorRequest;
import com.example.advisor_backend.model.dto.entity.FundFactor;
import com.example.advisor_backend.service.DerivedFactorService;
import com.example.advisor_backend.mapper.FundFactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DerivedFactorServiceImpl implements DerivedFactorService {
    @Autowired
    private FundFactorMapper fundFactorMapper;
    @Autowired
    private DerivedFactorRelationMapper derivedFactorRelationMapper;

    @Override
    public Long createDerivedFactor(CreateDerivedFactorRequest request) {
        // 1. 新增衍生因子（FundFactor表，type设为"DERIVED"）
        FundFactor factor = new FundFactor();
        factor.setName(request.getName());
        factor.setDescription(request.getDescription());
        factor.setType("DERIVED");
        fundFactorMapper.insert(factor);
        Long derivedId = factor.getId();
        // 2. 新增关系表
        for (CreateDerivedFactorRequest.BaseFactorWeight base : request.getBaseFactors()) {
            derivedFactorRelationMapper.insertRelation(derivedId, base.getId(), base.getWeight());
        }
        return derivedId;
    }
} 