// src/main/java/com/example/advisor_backend/model/entity/NavHistory.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="nav_history")
@Getter @Setter
public class NavHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;


    @Column(name = "nav", precision = 18, scale = 6, nullable = false)
    private BigDecimal nav;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code",
            referencedColumnName = "code" )
    private Fund fund;
}
