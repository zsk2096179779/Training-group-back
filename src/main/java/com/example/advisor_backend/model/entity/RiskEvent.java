package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
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
