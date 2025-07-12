package com.example.advisor_backend.repository;

import com.example.advisor_backend.model.entity.ErrorOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorOrderRepository extends JpaRepository<ErrorOrder, Long> { }