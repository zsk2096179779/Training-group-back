// src/main/java/com/example/advisor_backend/model/entity/Announcement.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="announcement")
@Getter @Setter
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "title", length = 500, nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code",
            referencedColumnName = "code" )
    private Fund fund;
}
