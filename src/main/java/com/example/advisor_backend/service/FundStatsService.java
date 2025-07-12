// src/main/java/com/example/advisor_backend/service/FundStatsService.java
package com.example.advisor_backend.service;

public interface FundStatsService {
    double getAnnualizedReturn(String code);
    double getSharpeRatio(String code);
    double getMaxDrawdown(String code);
}
