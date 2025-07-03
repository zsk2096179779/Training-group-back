// src/main/java/com/example/advisor_backend/model/entity/Holding.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name="holding")
@Getter @Setter
public class Holding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "asset", length = 200, nullable = false)
    private String asset;


    @Column(name = "percent", precision = 10, scale = 4, nullable = false)
    private BigDecimal percent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code",
            referencedColumnName = "code" )
    private Fund fund;
}
