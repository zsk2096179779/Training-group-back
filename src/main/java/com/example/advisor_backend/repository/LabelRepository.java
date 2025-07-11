// src/main/java/com/example/advisor_backend/repository/LabelRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> { }
