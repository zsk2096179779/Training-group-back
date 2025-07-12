package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.CreateDerivedFactorRequest;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.DerivedFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/derived-factor")
@CrossOrigin
public class DerivedFactorController {
    @Autowired
    private DerivedFactorService derivedFactorService;

    @PostMapping("/create")
    public ApiResponse<Long> createDerivedFactor(@Valid @RequestBody CreateDerivedFactorRequest request) {
        Long id = derivedFactorService.createDerivedFactor(request);
        return ApiResponse.ok(id);
    }
} 