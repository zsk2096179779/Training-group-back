package com.example.advisor_backend.model.dto.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DataSourceConfig {
    private Long id;
    private String name;
    private String type; // 如mysql
    private String configJson; // 连接参数、表名、字段映射等
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 