package com.example.advisor_backend.model.dto.entity;

public class ScriptExecutionResult {
    private String log;

    public ScriptExecutionResult(String log) {
        this.log = log;
    }

    // Getter and Setter
    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }
}