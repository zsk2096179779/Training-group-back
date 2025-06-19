package com.example.advisor_backend.model.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateDerivedFactorRequest {
    private String name;
    private String description;
    private List<BaseFactorWeight> baseFactors;

    @Data
    public static class BaseFactorWeight {
        private Long id;      // 基础因子ID
        private Double weight; // 权重
    }
} 