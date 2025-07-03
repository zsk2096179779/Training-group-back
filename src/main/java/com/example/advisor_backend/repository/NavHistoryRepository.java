// src/main/java/com/example/advisor_backend/repository/NavHistoryRepository.java
package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.NavHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NavHistoryRepository extends JpaRepository<NavHistory, Long> {
    List<NavHistory> findByFund_Code(String code);
}
