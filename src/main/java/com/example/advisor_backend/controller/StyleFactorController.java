package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.entity.StyleFactor;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.StyleFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping("/api/style-factors")
@CrossOrigin
public class StyleFactorController {
    @Autowired
    private StyleFactorService styleFactorService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<StyleFactor>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(styleFactorService.getAll()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> create(@RequestBody StyleFactor styleFactor) {
        // 校验权重和为100（前端传递的 weights 是 JSON 字符串）
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> weightsList = mapper.readValue(styleFactor.getWeights(), new TypeReference<List<Map<String, Object>>>(){});
            double sum = weightsList.stream().mapToDouble(w -> Double.parseDouble(w.get("weight").toString())).sum();
            if (sum != 100.0) {
                return ResponseEntity.badRequest().body(ApiResponse.error(400, "权重总和必须为100"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(400, "weights 字段格式错误"));
        }
        styleFactorService.create(styleFactor);
        return ResponseEntity.ok(ApiResponse.ok(styleFactor.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        styleFactorService.delete(id);
        return ResponseEntity.ok(ApiResponse.ok(null));
    }
} 