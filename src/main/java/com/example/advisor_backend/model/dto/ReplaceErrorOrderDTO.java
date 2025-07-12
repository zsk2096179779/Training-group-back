package com.example.advisor_backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplaceErrorOrderDTO {
    private Long orderId;
    private Long newFundId;   // 新的基金ID
}
