package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);
    List<Order> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Order> findByUserIdAndProductIdAndOrderTypeIn(Long userId, Long productId, List<String> orderTypes);
    List<Order> findByUserIdAndOrderTypeIn(Long userId, List<String> orderTypes);
} 