package com.example.advisor_backend.model.entity;
// 请在 dto 包下创建这个文件
// com.example.advisor_backend.model.dto.ConnectionConfig
public class ConnectionConfig {
    private String host;
    private String port;
    private String username;
    private String password;
    private String database;
    private String tableName;
    // 如果您的JSON里还有其他字段，也一并加在这里

    // --- 在此添加所有字段的 Getter 和 Setter ---
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getDatabase() { return database; }
    public void setDatabase(String database) { this.database = database; }
    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }
}