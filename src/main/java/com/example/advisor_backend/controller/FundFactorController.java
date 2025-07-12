package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.BaseFactorRef;
import com.example.advisor_backend.model.dto.PreviewFormulaRequest;
import com.example.advisor_backend.model.entity.FundFactor;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.service.FundFactorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/factors")
@CrossOrigin
public class FundFactorController {

    @Autowired
    private FundFactorService fundFactorService;
    
    // 测试端点，用于调试
    @GetMapping("/test")
    public ApiResponse<String> testConnection() {
        try {
            List<FundFactor> factors = fundFactorService.getAllFactors();
            return ApiResponse.ok("连接成功，查询到 " + factors.size() + " 条记录");
        } catch (Exception e) {
            return ApiResponse.error(500, "连接失败: " + e.getMessage());
        }
    }
    
    @GetMapping("/listByNodeRecursive")
    public ApiResponse<List<FundFactor>> listByNodeRecursive(@RequestParam Long nodeId) {
        List<FundFactor> factors = fundFactorService.getFactorsByNodeRecursive(nodeId);
        return ApiResponse.ok(factors);
    }
    // 1. 查询某节点下所有因子（点击树节点时用）
    @GetMapping("/listByNode")
    public ApiResponse<List<FundFactor>> listByNode(@RequestParam Long nodeId) {
        List<FundFactor> factors = fundFactorService.getFactorsByNode(nodeId);
        return ApiResponse.ok(factors);
    }

    // 2. 查询所有因子
    @GetMapping
    public ApiResponse<List<FundFactor>> getAllFactors() {
        List<FundFactor> list = fundFactorService.getAllFactors();
        return ApiResponse.ok(list);
    }

    // 3. 根据ID查询
    @GetMapping("/{id}")
    public ApiResponse<FundFactor> getFactorById(@PathVariable Long id) {
        FundFactor factor = fundFactorService.getFactorById(id);
        return factor != null ? ApiResponse.ok(factor) : ApiResponse.error(500,"因子不存在");
    }

    // 4. 创建因子
    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // 临时注释掉以便测试
    public ApiResponse<String> createFactor(@Valid @RequestBody FundFactor factor) {
        // 添加调试信息
        System.out.println("接收到创建因子请求，name: " + factor.getName());
        
        // 手动验证名称不能为空
        if (factor.getName() == null || factor.getName().trim().isEmpty()) {
            System.out.println("验证失败：因子名称不能为空");
            return ApiResponse.error(400, "因子名称不能为空");
        }
        
        System.out.println("验证通过，调用service创建因子");
        boolean success = fundFactorService.createFactor(factor);
        return success ? ApiResponse.ok("创建成功") : ApiResponse.error(500,"创建失败");
    }
    @PostMapping("/previewFormula")
    // @PreAuthorize("hasRole('ADMIN')") // 临时注释掉以便测试
    public ApiResponse<Map<String, Object>> previewFormula(@RequestBody PreviewFormulaRequest req) {
        System.out.println("收到公式: " + req.getFormula());
        if (req.getBaseFactors() != null) {
            for (BaseFactorRef ref : req.getBaseFactors()) {
                System.out.println("基础因子ID: " + ref.getBaseFactorId() + ", 权重: " + ref.getWeight());
            }
        }

        if (req.getFormula() == null || req.getFormula().trim().isEmpty()) {
            return ApiResponse.error(400, "公式不能为空");
        }
        // 返回 result 字段，兼容前端
        Map<String, Object> result = new HashMap<>();
        result.put("result", "公式预览成功：" + req.getFormula());
        return ApiResponse.ok(result);
    }
    // 5. 更新因子
    @PutMapping
    // @PreAuthorize("hasRole('ADMIN')") // 临时注释掉以便测试
    public ApiResponse<String> updateFactor(@RequestBody FundFactor factor) {
        boolean success = fundFactorService.updateFactor(factor);
        return success ? ApiResponse.ok("更新成功") : ApiResponse.error(500,"更新失败");
    }

    // 6. 删除因子
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // 临时注释掉以便测试
    public ApiResponse<String> deleteFactor(@PathVariable Long id) {
        boolean success = fundFactorService.deleteFactor(id);
        return success ? ApiResponse.ok("删除成功") : ApiResponse.error(500,"删除失败");
    }
}
