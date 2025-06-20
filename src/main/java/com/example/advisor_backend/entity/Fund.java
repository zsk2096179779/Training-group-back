package com.example.advisor_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Fund {
    private String fundCode; // 主键：基金代码
    private String fundName;
    private String category;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
