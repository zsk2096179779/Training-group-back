package com.example.advisor_backend.bean;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "strategy",
        indexes = {
                @Index(name = "idx_owner", columnList = "owner"),
                @Index(name = "idx_create_time", columnList = "create_time")
        })
public class Strategy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "type", nullable = false)
    private int type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private Status status = Status.active;

    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "owner")
    private Integer owner;

    // 枚举类型定义策略状态
    public enum Status {
        active,     // 生效中
        stop,   // 已失效
        warn     // 有风险
    }

    // 构造函数
    public Strategy() {
    }

    public Strategy(String name, int type, int owner) {
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    // 建议重写toString()方法
    @Override
    public String toString() {
        return "Strategy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }
}