package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "strategy",
        indexes = {
                @Index(name = "idx_owner", columnList = "owner"),
                @Index(name = "idx_create_time", columnList = "create_time")
        })
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "type", nullable = false)
    private int type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.stop;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "owner")
    private Integer owner;

    @Column(name ="gain")
    private Double gain;

    // 枚举类型定义策略状态
    public enum Status {
        active,     // 生效中
        stop,   // 已失效
        warn     // 有风险
    }

    public Strategy() {
    }

    public Strategy(String name, int type) {
        this.name = name;
        this.type = type;
        this.status=Status.stop;
        this.owner=1;
    }

    public Strategy(Integer id, String name, int type, Status status, LocalDateTime createTime, Integer owner, Double gain) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
        this.owner = owner;
        this.gain = gain;
    }

    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", owner=" + owner +
                ", gain=" + gain +
                '}';
    }
}