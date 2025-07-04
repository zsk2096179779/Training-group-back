package com.example.fundadvisorserver.repository;

import com.example.fundadvisorserver.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
} 