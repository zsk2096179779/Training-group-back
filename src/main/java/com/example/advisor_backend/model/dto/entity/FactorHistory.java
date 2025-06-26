package com.example.advisor_backend.model.dto.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class FactorHistory {
    private Integer id;
    private Integer factorId;
    private LocalDate date;
    private BigDecimal value;
} 