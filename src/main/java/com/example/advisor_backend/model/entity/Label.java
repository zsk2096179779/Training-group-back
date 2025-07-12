// src/main/java/com/example/advisor_backend/model/entity/Label.java
package com.example.advisor_backend.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "label")
@Getter @Setter @NoArgsConstructor
public class Label {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=100)
    private String name;

    @ManyToMany(mappedBy = "labels")
    private Set<Fund> funds;
}
