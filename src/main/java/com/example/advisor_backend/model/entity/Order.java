package com.example.advisor_backend.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long strategyId;
    private String fundCode;
    private Double amount;
    private String status;
    private Double oldRatio;
    private Double newRatio;
    private LocalDateTime createTime;
    private String type;

    // 新增字段，确保与数据库列一致
    private Long account_Id;  // 对应 account_id
    private Long fund_Id;     // 对应 fund_id
}