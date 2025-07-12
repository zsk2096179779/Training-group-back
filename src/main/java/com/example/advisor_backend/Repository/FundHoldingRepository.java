// src/main/java/com/example/advisor_backend/repository/HoldingRepository.java
package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.FundHolding;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FundHoldingRepository extends JpaRepository<FundHolding, Long> {
    List<FundHolding> findByFund_Code(String code);
}