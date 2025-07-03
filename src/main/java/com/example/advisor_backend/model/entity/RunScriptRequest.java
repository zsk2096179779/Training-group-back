package com.example.advisor_backend.model.dto.entity;

// 在你的dto或model包下创建这个文件
public class RunScriptRequest {
    private Long id; // 数据源配置的ID
    private String script; // 用户提交的Python脚本内容

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}