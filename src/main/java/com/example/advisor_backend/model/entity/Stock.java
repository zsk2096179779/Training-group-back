package com.example.advisor_backend.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "stocks")
@Data
public class Stock {
    @Id
    @Column(name = "stock_code")
    private String code;

    @Column(name = "stock_name")
    private String name;

    public Stock() {
    }

    public Stock(String code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}