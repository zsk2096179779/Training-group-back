// src/main/java/com/example/advisor_backend/controller/StrategyRebalanceController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.StrategyIdRequest;
import com.example.advisor_backend.model.dto.BacktestRequest;
import com.example.advisor_backend.model.dto.BacktestResponse;
import com.example.advisor_backend.model.entity.RebalanceSetting;
import com.example.advisor_backend.service.ServiceImpl.RebalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/strategy-rebalance")
public class StrategyRebalanceController {

    @Autowired
    private RebalanceServiceImpl rebalanceService;

    /**
     * 获取某个策略的再平衡设置
     * POST /api/strategy-rebalance/detail
     * Body: { "strategyId": 123 }
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getRebalanceDetail(@RequestBody StrategyIdRequest req) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            RebalanceSetting setting = rebalanceService.getRebalanceById(req.getStrategyId());
            if (setting == null) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "未找到对应的再平衡设置"));
            }
            return ResponseEntity.ok(Map.of("success", true, "data", setting));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "获取再平衡设置失败: " + ex.getMessage()));
        }
    }

    /**
     * 更新某个策略的再平衡设置
     * POST /api/strategy-rebalance/update
     * Body: RebalanceSetting 的 JSON
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateRebalance(@RequestBody RebalanceSetting setting) {
        if (setting.getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少再平衡设置的 ID"));
        }
        try {
            rebalanceService.save(setting);
            return ResponseEntity.ok(Map.of("success", true, "message", "再平衡设置更新成功"));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "更新再平衡设置失败: " + ex.getMessage()));
        }
    }

    /**
     * 对某个策略执行回测
     * POST /api/strategy-rebalance/backtest
     * Body: BacktestRequest JSON
     */
    @PostMapping("/backtest")
    public ResponseEntity<?> runBacktest(@RequestBody BacktestRequest request) {
        try {
            // 调用 service 进行回测
            BacktestResponse response = rebalanceService.runBacktest(request);
            return ResponseEntity.ok(Map.of("success", true, "data", response));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "回测执行失败: " + ex.getMessage()));
        }
    }
}
