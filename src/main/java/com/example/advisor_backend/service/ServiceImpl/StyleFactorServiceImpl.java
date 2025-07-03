package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.StyleFactorMapper;
import com.example.advisor_backend.model.dto.entity.StyleFactor;
import com.example.advisor_backend.service.StyleFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StyleFactorServiceImpl implements StyleFactorService {
    @Autowired
    private StyleFactorMapper styleFactorMapper;

    @Override
    public List<StyleFactor> getAll() {
        return styleFactorMapper.selectAll();
    }

    @Override
    public void create(StyleFactor styleFactor) {
        styleFactorMapper.insert(styleFactor);
    }

    @Override
    public void delete(Long id) {
        styleFactorMapper.deleteById(id);
    }
} 