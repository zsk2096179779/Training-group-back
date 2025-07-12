package com.example.advisor_backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 这是您的实体类，路径应为：
// com.example.advisor_backend.model.entity.DataSourceConfig
@Setter
@Getter
@Data
@Entity
@Table(name = "data_source_config")
public class DataSourceConfig {
    // --- 确保所有字段都有 Getter 和 Setter ---
    @Id
    private Long id;
    private String name;
    private String type;
    private String configJson; // 对应数据库的 config_json
    private java.util.Date createTime;
    private java.util.Date updateTime;

}