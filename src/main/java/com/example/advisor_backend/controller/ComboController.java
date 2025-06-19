package com.example.advisor_backend.controller;

import com.example.advisor_backend.entity.Combo;
import com.example.advisor_backend.entity.Fund;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/combos")
@Slf4j
public class ComboController {
    @Autowired
    private ComboService comboService;
    @GetMapping
    public ApiResponse<List<Combo>> getAllCombos() {
        log.info("查询全部基金组合数据");
        List<Combo> productList = comboService.list();
        return ApiResponse.ok(productList);
    }

}
