package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.ComboCreateRequest;
import com.example.advisor_backend.model.entity.Combo;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.model.entity.Fund;
import com.example.advisor_backend.service.ComboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//允许跨域，后续更改前端api
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/combos")
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

    @GetMapping("/funds/all")
    public ApiResponse<List<Fund>> getFundsAll(){
        return ApiResponse.ok(comboService.getFundsAll());
    }

    @PostMapping("/create")
    public ApiResponse<String> createCombo(@RequestBody ComboCreateRequest req) {
        log.info("接收到创建组合请求：{}", req);
        comboService.createCombo(req);
        return ApiResponse.ok("组合创建成功");
    }

}
