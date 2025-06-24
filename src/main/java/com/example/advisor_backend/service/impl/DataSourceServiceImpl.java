// com.example.advisor_backend.service.impl.DataSourceServiceImpl
package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.DataSourceConfigMapper;
import com.example.advisor_backend.mapper.FundFactorMapper;
import com.example.advisor_backend.model.dto.entity.ConnectionConfig;
import com.example.advisor_backend.model.dto.entity.DataSourceConfig;
import com.example.advisor_backend.model.dto.entity.FundFactor;
import com.example.advisor_backend.model.dto.entity.RunScriptRequest;
import com.example.advisor_backend.model.dto.entity.ScriptExecutionResult;
import com.example.advisor_backend.service.DataSourceService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

@Service
public class DataSourceServiceImpl implements DataSourceService {

    @Autowired
    private DataSourceConfigMapper configMapper;

    @Autowired
    private FundFactorMapper fundFactorMapper;

    // 建议使用Spring注入的ObjectMapper实例
    @Autowired
    private ObjectMapper objectMapper;

    // --- 您已有的方法 (保持不变) ---
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
    public ScriptExecutionResult runScript(RunScriptRequest request) {
        if (request.getId() == null) {
            return new ScriptExecutionResult("ERROR: DataSource ID is required to run a script.");
        }
        
        DataSourceConfig config = configMapper.selectById(request.getId());
        if (config == null) {
            return new ScriptExecutionResult("ERROR: DataSource with ID " + request.getId() + " not found.");
        }

        String scriptContent = request.getScript();
        StringBuilder log = new StringBuilder();

        try {
            ConnectionConfig connConfig = objectMapper.readValue(config.getConfigJson(), ConnectionConfig.class);

            Path scriptPath = Files.createTempFile("user_script_", ".py");
            Files.writeString(scriptPath, scriptContent);

            ProcessBuilder pb = new ProcessBuilder("python",
                    scriptPath.toAbsolutePath().toString(),
                    "--host", connConfig.getHost(),
                    "--port", connConfig.getPort(),
                    "--user", connConfig.getUsername(),
                    "--password", connConfig.getPassword(),
                    "--database", connConfig.getDatabase(),
                    "--table", connConfig.getTableName()
            );

            Process process = pb.start();

            // 读取脚本的输出
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.append(line).append(System.lineSeparator());
                }
            }

            // 读取脚本的错误输出
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    log.append("ERROR: ").append(line).append(System.lineSeparator());
                }
            }

            int exitCode = process.waitFor();
            log.append("Python script exited with code: ").append(exitCode);

            // 清理临时文件
            Files.delete(scriptPath);

        } catch (IOException | InterruptedException e) {
            log.append("Failed to execute script: ").append(e.getMessage());
            Thread.currentThread().interrupt();
        }
        return new ScriptExecutionResult(log.toString());
    }
    @Override
    public int importToFundFactor(Long configId) {
        DataSourceConfig config = configMapper.selectById(configId);
        if (config == null) return 0;
        try {
            JsonNode cfg = objectMapper.readTree(config.getConfigJson());
            if (!"mysql".equalsIgnoreCase(config.getType())) return 0;
            String url = String.format("jdbc:mysql://%s:%s/%s?useSSL=false&serverTimezone=UTC",
                    cfg.get("host").asText(), cfg.get("port").asText(), cfg.get("database").asText());
            String username = cfg.get("username").asText();
            String password = cfg.get("password").asText();
            String table = cfg.get("tableName").asText(); // 注意: 前端传的是tableName
            JsonNode mapping = cfg.get("fieldMapping");

            List<String> extFields = new ArrayList<>();
            mapping.fields().forEachRemaining(e -> extFields.add(e.getValue().asText()));
            String sql = "SELECT " + String.join(",", extFields) + " FROM " + table;

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
