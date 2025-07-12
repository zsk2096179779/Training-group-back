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
@Table(name = "t_orders")
public class Order {
    @Id
    @GeneratedValue
    private Long id;
    private Long strategyId;
    private String fundCode;
    private Double amount;
    private String status;
    private LocalDateTime createdAt;
    // getters/setters...
}