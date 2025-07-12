package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RebalanceDetailDTO {
    private Long fundId;        // 基金ID
    private Double currentRatio; // 当前比例
    private Double targetRatio;  // 目标比例
}