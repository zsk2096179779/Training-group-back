package com.example.advisor_backend.model.dto.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StyleFactor {
    private Long id;
    private String name;
    private String weights; // 直接用 String 存储 JSON
    private LocalDateTime createTime;
} 