package com.example.advisor_backend.Repository;

import com.example.advisor_backend.model.entity.FillOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FillOrderRepository extends JpaRepository<FillOrder, Long> { }