package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.entity.FundFactor;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.FundFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/factors")
@CrossOrigin
public class FundFactorController {

    @Autowired
    private FundFactorService fundFactorService;

    // 查询所有因子
    @GetMapping
    public ApiResponse<List<FundFactor>> getAllFactors() {
        List<FundFactor> list = fundFactorService.getAllFactors();
        return ApiResponse.ok(list);
    }

    // 根据ID查询
    @GetMapping("/{id}")
    public ApiResponse<FundFactor> getFactorById(@PathVariable Long id) {
        FundFactor factor = fundFactorService.getFactorById(id);
        return factor != null ? ApiResponse.ok(factor) : ApiResponse.error(500,"因子不存在");
    }

    // 创建因子
    @PostMapping
    public ApiResponse<String> createFactor(@RequestBody FundFactor factor) {
        boolean success = fundFactorService.createFactor(factor);
        return success ? ApiResponse.ok("创建成功") : ApiResponse.error(500,"创建失败");
    }

    // 更新因子
    @PutMapping
    public ApiResponse<String> updateFactor(@RequestBody FundFactor factor) {
        boolean success = fundFactorService.updateFactor(factor);
        return success ? ApiResponse.ok("更新成功") : ApiResponse.error(500,"更新失败");
    }

    // 删除因子
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteFactor(@PathVariable Long id) {
        boolean success = fundFactorService.deleteFactor(id);
        return success ? ApiResponse.ok("删除成功") : ApiResponse.error(500,"删除失败");
    }
}
