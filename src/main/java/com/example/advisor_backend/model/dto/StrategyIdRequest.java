// src/main/java/com/example/advisor_backend/model/dto/StrategyIdRequest.java
package com.example.advisor_backend.model.dto;

public class StrategyIdRequest {
    /** 要查询监控的策略 ID */
    private Integer strategyId;

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }
}
