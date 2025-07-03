package com.example.advisor_backend.model.dto.entity;

import lombok.Data;
import java.time.LocalDateTime;

// 这是您的实体类，路径应为：
// com.example.advisor_backend.model.dto.entity.DataSourceConfig
public class DataSourceConfig {
    private Long id;
    private String name;
    private String type;
    private String configJson; // 对应数据库的 config_json
    private java.util.Date createTime;
    private java.util.Date updateTime;

    // --- 确保所有字段都有 Getter 和 Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getConfigJson() { return configJson; }
    public void setConfigJson(String configJson) { this.configJson = configJson; }
    public java.util.Date getCreateTime() { return createTime; }
    public void setCreateTime(java.util.Date createTime) { this.createTime = createTime; }
    public java.util.Date getUpdateTime() { return updateTime; }
    public void setUpdateTime(java.util.Date updateTime) { this.updateTime = updateTime; }
}