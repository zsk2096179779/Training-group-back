package com.example.advisor_backend.bean;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_event")
@Data
public class RiskEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "strategy_id", nullable = false)
    private Integer strategyId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevel riskLevel;

    @Column(name = "is_resolved", nullable = false)
    private Boolean resolved = false;

    public enum RiskLevel {
        HIGH, MEDIUM, LOW
    }

    public RiskEvent() {
    }

    public RiskEvent(Integer id, Integer strategyId, String title, LocalDateTime eventTime, String description, RiskLevel riskLevel, Boolean resolved) {
        this.id = id;
        this.strategyId = strategyId;
        this.title = title;
        this.eventTime = eventTime;
        this.description = description;
        this.riskLevel = riskLevel;
        this.resolved = resolved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RiskLevel getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }

    public Boolean getResolved() {
        return resolved;
    }

    public void setResolved(Boolean resolved) {
        this.resolved = resolved;
    }

    @Override
    public String toString() {
        return "RiskEvent{" +
                "id=" + id +
                ", strategyId=" + strategyId +
                ", title='" + title + '\'' +
                ", eventTime=" + eventTime +
                ", description='" + description + '\'' +
                ", riskLevel=" + riskLevel +
                ", resolved=" + resolved +
                '}';
    }
}
