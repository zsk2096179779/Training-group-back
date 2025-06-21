package com.example.advisor_backend.controller;

import com.example.advisor_backend.entity.Combo;
import com.example.advisor_backend.entity.Fund;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//允许跨域，后续更改前端api
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/combos")
@Slf4j
public class ComboController {
    @Autowired
    private ComboService comboService;

    @GetMapping
    public ApiResponse<List<Combo>> getAllCombos() {
        log.info("查询全部基金组合数据");
        List<Combo> combosList = comboService.list();
        log.info(combosList.toString());
        return ApiResponse.ok(combosList);
    }


    @GetMapping("/{id}")
    public ApiResponse<List<Integer>> getFundId(@PathVariable Long id){
        log.info("查询组合中基金id");
        List<Integer> fundId = comboService.getFundId(id);
        return ApiResponse.ok(fundId);
    }


    @GetMapping("/funds")
    public ApiResponse<List<Fund>> getFunds(@RequestParam List<Integer> ids) {
        return ApiResponse.ok(comboService.getFunds(ids));
    }

}
