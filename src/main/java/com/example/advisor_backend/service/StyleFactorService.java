package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.StyleFactor;
import java.util.List;

public interface StyleFactorService {
    List<StyleFactor> getAll();
    void create(StyleFactor styleFactor);
    void delete(Long id);
} 