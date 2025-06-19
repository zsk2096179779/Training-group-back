package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.DataSourceConfigMapper;
import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.model.dto.entity.DataSourceConfig;
import com.example.advisor_backend.model.dto.entity.FundFactor;
import com.example.advisor_backend.service.DataSourceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.*;

@Service
public class DataSourceServiceImpl implements DataSourceService {
    @Autowired
    private DataSourceConfigMapper configMapper;
    @Autowired
    private FundFactorMapper fundFactorMapper;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public int addConfig(DataSourceConfig config) {
        return configMapper.insert(config);
    }

    @Override
    public int updateConfig(DataSourceConfig config) {
        return configMapper.update(config);
    }

    @Override
    public int deleteConfig(Long id) {
        return configMapper.deleteById(id);
    }

    @Override
    public DataSourceConfig getConfig(Long id) {
        return configMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfig> getAllConfigs() {
        return configMapper.selectAll();
    }

    @Override
    public int importToFundFactor(Long configId) {
        DataSourceConfig config = configMapper.selectById(configId);
        if (config == null) return 0;
        try {
            JsonNode cfg = objectMapper.readTree(config.getConfigJson());
            if (!"mysql".equalsIgnoreCase(config.getType())) return 0;
            // 解析连接参数
            String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC",
                    cfg.get("host").asText(),
                    cfg.get("port").asText(),
                    cfg.get("database").asText());
            String username = cfg.get("username").asText();
            String password = cfg.get("password").asText();
            String table = cfg.get("table").asText();
            JsonNode mapping = cfg.get("fieldMapping");
            // 构建SQL
            List<String> extFields = new ArrayList<>();
            mapping.fields().forEachRemaining(e -> extFields.add(e.getValue().asText()));
            String sql = "SELECT " + String.join(",", extFields) + " FROM " + table;
            // 查询外部数据
            List<FundFactor> factors = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    FundFactor f = new FundFactor();
                    mapping.fields().forEachRemaining(e -> {
                        String localField = e.getKey();
                        String extField = e.getValue().asText();
                        try {
                            Object value = rs.getObject(extField);
                            if (value != null) {
                                switch (localField) {
                                    case "name": f.setName(value.toString()); break;
                                    case "code": f.setCode(value.toString()); break;
                                    case "category": f.setCategory(value.toString()); break;
                                    case "description": f.setDescription(value.toString()); break;
                                    case "type": f.setType(value.toString()); break;
                                }
                            }
                        } catch (SQLException ex) { /* ignore */ }
                    });
                    factors.add(f);
                }
            }
            // 批量插入
            int count = 0;
            for (FundFactor f : factors) {
                count += fundFactorMapper.insert(f);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
} 