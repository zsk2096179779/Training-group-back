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
@Table(name = "t_error_orders")
public class ErrorOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Long originalOrderId;
    private String fundCode;
    private String errorMsg;
    private boolean resolved;
    private LocalDateTime createdAt;

    private long fundId;
    // getters/setters...
}