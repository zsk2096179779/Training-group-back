// src/main/java/com/example/advisor_backend/repository/FundRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.Fund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface FundRepository
        extends JpaRepository<Fund, Long>,
        JpaSpecificationExecutor<Fund> {
    Optional<Fund> findByCode(String code);
    // SpecificationExecutor 用于动态过滤
}
