package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderQueryDTO {
    private String type;       // "open" | "rebal" | "error"
    private Long strategyId;   // 可选：策略ID
}

