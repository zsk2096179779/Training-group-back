// src/main/java/com/example/advisor_backend/model/entity/Attribution.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="attribution")
@Getter @Setter
public class Attribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category", length = 100, nullable = false)
    private String category;


    @Column(name="weight", nullable=false)
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code",
            referencedColumnName = "code" )
    private Fund fund;
}
