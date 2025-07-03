// src/main/java/com/example/advisor_backend/repository/HoldingRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.Holding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HoldingRepository extends JpaRepository<Holding, Long> {
    List<Holding> findByFund_Code(String code);
}
