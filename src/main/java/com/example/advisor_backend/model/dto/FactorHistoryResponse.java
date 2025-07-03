package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class FactorHistoryResponse {
    private String name;
    private List<String> dates;
    private List<Double> values;
} 