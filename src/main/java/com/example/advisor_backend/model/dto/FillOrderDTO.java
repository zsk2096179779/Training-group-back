package com.example.advisor_backend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FillOrderDTO {
    private Long fillId;
    private Long orderId;
    private Double filledAmount;
    private String status;
    private String filledDate;
}
