// src/main/java/com/example/advisor_backend/repository/AttributionRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.Attribution;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AttributionRepository extends JpaRepository<Attribution, Long> {
    List<Attribution> findByFund_Code(String code);
}
