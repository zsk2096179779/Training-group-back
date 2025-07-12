package com.example.advisor_backend.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "t_fill_orders")
public class FillOrder {
    @Id
    @GeneratedValue
    private Long id;
    private Long orderId;
    private String fundCode;
    private Double fillAmount;
    private LocalDateTime fillDate;
    // getters/setters...
}