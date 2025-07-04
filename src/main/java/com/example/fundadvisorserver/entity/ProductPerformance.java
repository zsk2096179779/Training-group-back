package com.example.fundadvisorserver.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "product_performance")
public class ProductPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Date date;
    private Double nav;
    private Double dailyChange;
    private Timestamp createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public Double getNav() { return nav; }
    public void setNav(Double nav) { this.nav = nav; }
    public Double getDailyChange() { return dailyChange; }
    public void setDailyChange(Double dailyChange) { this.dailyChange = dailyChange; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
} 