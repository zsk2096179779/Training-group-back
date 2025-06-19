package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.model.dto.entity.FundFactor;
import com.example.advisor_backend.service.FundFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FundFactorServiceImpl implements FundFactorService {

    @Autowired
    private FundFactorMapper fundFactorMapper;

    @Override
    public List<FundFactor> getAllFactors() {
        return fundFactorMapper.selectAll();
    }

    @Override
    public FundFactor getFactorById(Long id) {
        return fundFactorMapper.selectById(id);
    }

    @Override
    public boolean createFactor(FundFactor factor) {
        return fundFactorMapper.insert(factor) > 0;
    }

    @Override
    public boolean updateFactor(FundFactor factor) {
        return fundFactorMapper.update(factor) > 0;
    }

    @Override
    public boolean deleteFactor(Long id) {
        return fundFactorMapper.deleteById(id) > 0;
    }
}
