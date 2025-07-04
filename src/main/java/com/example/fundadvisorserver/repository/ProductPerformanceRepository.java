package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.ProductPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductPerformanceRepository extends JpaRepository<ProductPerformance, Long> {
    List<ProductPerformance> findByProductIdOrderByDateAsc(Long productId);
} 