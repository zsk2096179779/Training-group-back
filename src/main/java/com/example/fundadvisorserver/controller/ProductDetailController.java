package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.Product;
import com.example.fundadvisorserver.entity.ProductPerformance;
import com.example.fundadvisorserver.repository.ProductRepository;
import com.example.fundadvisorserver.repository.ProductPerformanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductDetailController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPerformanceRepository performanceRepository;

    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestParam Long id) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("产品不存在");
        }
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("产品不存在");
        }
        Product product = productOpt.get();

        // 查询曲线数据
        List<ProductPerformance> perfList = performanceRepository.findByProductIdOrderByDateAsc(id);
        List<Map<String, Object>> curveData = new ArrayList<>();
        for (ProductPerformance perf : perfList) {
            Map<String, Object> point = new HashMap<>();
            point.put("date", perf.getDate().toString());
            point.put("value", perf.getNav());
            curveData.add(point);
        }

        // 构造返回
        Map<String, Object> result = new HashMap<>();
        result.put("id", product.getId());
        result.put("name", product.getName());
        result.put("risk", product.getTag()); // 假设tag为风险
        result.put("type", product.getType());
        result.put("minPurchase", product.getMinPurchase());
        result.put("yieldRate", product.getYieldRate());
        result.put("yieldPeriod", product.getYieldPeriod());
        result.put("description", product.getDescription());
        result.put("intro", product.getDescription()); // 可自定义
        result.put("curveData", curveData);
        result.put("riskLevel", product.getRiskLevel()); // 新增：风险等级

        return ResponseEntity.ok(result);
    }
} 