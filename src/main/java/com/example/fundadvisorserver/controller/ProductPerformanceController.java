package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.ProductPerformance;
import com.example.fundadvisorserver.repository.ProductPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductPerformanceController {
    @Autowired
    private ProductPerformanceRepository performanceRepository;

    @GetMapping("/performance")
    public List<ProductPerformance> getPerformance(@RequestParam Long productId) {
        return performanceRepository.findByProductIdOrderByDateAsc(productId);
    }
} 