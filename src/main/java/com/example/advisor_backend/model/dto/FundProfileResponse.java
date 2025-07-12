// src/main/java/com/example/advisor_backend/model/dto/FundProfileResponse.java
package com.example.advisor_backend.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FundProfileResponse {
    // 基本信息
    private String code;
    private String name;
    private String companyName;
    private String managerName;
    private LocalDate inceptionDate;

    // 最新净值 & 风险
    private BigDecimal nav;
    private Double risk;

    // 净值历史，用于折线图
    private List<TimeValue> navHistory;

    // 业绩指标（年化、夏普率、最大回撤等）
    private List<Metric> metrics;

    // 持仓分析（股票/债券占比）
    private List<Holding> holdings;

    // 业绩归因（行业/风格）饼图
    private List<Attribution> attribution;

    // 公告列表
    private List<Announcement> announcements;

    @Data public static class TimeValue {
        private String date;    // yyyy-MM-dd
        private BigDecimal value;
    }
    @Data public static class Metric {
        private String name;
        private double value;
        public Metric(String name, double value) {
            this.name = name;
            this.value = value;
        }
    }
    @Data public static class Holding {
        private String name;    // 持仓标的
        private BigDecimal percent; // 占比
    }
    @Data public static class Attribution {
        private String category; // “行业：金融”
        private Double value;    // 权重
    }
    @Data public static class Announcement {
        private LocalDate date;
        private String title;
    }
}
