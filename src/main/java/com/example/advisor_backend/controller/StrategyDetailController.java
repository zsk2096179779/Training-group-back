// 文件路径: src/main/java/com/example/advisor_backend/controller/StrategyDetailController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.StrategyDetailRequest;
import com.example.advisor_backend.model.entity.Detail;
import com.example.advisor_backend.model.entity.ProfitChart;
import com.example.advisor_backend.model.entity.StrategyHolding;
import com.example.advisor_backend.model.entity.TradeHistory;
import com.example.advisor_backend.model.entity.User;
import com.example.advisor_backend.service.ServiceImpl.DetailServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.ProfitChartServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.StrategyHoldingServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.TradeHistoryServiceImpl;
import com.example.advisor_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/strategy-management")
public class StrategyDetailController {

    @Autowired
    private UserService userService;
    @Autowired
    private DetailServiceImpl detailService;
    @Autowired
    private ProfitChartServiceImpl profitChartService;
    @Autowired
    private StrategyHoldingServiceImpl holdingService;
    @Autowired
    private TradeHistoryServiceImpl tradeHistoryService;

    /**
     * 查看策略详情
     * POST /api/strategy-management/detail
     * 请求体: { "username": "...", "strategyId": 123 }
     */
    @PostMapping("/detail")
    public ResponseEntity<?> getStrategyDetail(@RequestBody StrategyDetailRequest req) {
        // 1. 校验参数
        if (req.getUsername() == null || req.getUsername().trim().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "用户名不能为空"));
        }
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "策略ID不能为空"));
        }

        // 2. 根据用户名查用户
        String username = req.getUsername().trim();
        User user = userService.getUserByName(username);
        if (user == null) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("success", false, "message", "用户不存在"));
        }

        // 3. 查详情并校验权限
        try {
            Detail detail = detailService.getDetailsById(req.getStrategyId().intValue());
            if (detail == null) {
                return ResponseEntity
                        .status(404)
                        .body(Map.of("success", false, "message", "策略详情未找到"));
            }
            // 假设 Detail.getStrategy().getOwner() 存放的是用户 ID
            if (!Objects.equals(detail.getStrategy().getOwner(), user.getId().intValue())) {
                return ResponseEntity
                        .status(403)
                        .body(Map.of("success", false, "message", "无权限访问该策略"));
            }

            return ResponseEntity
                    .ok(Map.of("success", true, "data", detail));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("success", false, "message", "获取策略详情失败: " + ex.getMessage()));
        }
    }

    /**
     * 查看策略收益曲线
     * POST /api/strategy-management/chart
     */
    @PostMapping("/chart")
    public ResponseEntity<?> getStrategyChart(@RequestBody StrategyDetailRequest req) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "策略ID不能为空"));
        }
        try {
            ProfitChart chart = profitChartService.getChartById(req.getStrategyId().intValue());
            if (chart == null) {
                return ResponseEntity
                        .status(404)
                        .body(Map.of("success", false, "message", "未找到收益曲线数据"));
            }
            return ResponseEntity
                    .ok(Map.of("success", true, "data", chart));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("success", false, "message", "获取收益曲线失败: " + ex.getMessage()));
        }
    }

    /**
     * 查看策略持仓
     * POST /api/strategy-management/holding
     */
    @PostMapping("/holding")
    public ResponseEntity<?> getStrategyHolding(@RequestBody StrategyDetailRequest req) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "策略ID不能为空"));
        }
        try {
            List<StrategyHolding> holdings = holdingService.getHoldingById(req.getStrategyId().intValue());
            return ResponseEntity
                    .ok(Map.of("success", true, "data", holdings));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("success", false, "message", "获取持仓失败: " + ex.getMessage()));
        }
    }

    /**
     * 查看策略交易历史
     * POST /api/strategy-management/trade-history
     */
    @PostMapping("/trade-history")
    public ResponseEntity<?> getStrategyTradeHistory(@RequestBody StrategyDetailRequest req) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "策略ID不能为空"));
        }
        try {
            List<TradeHistory> trades = tradeHistoryService.getTradeHistoryById(req.getStrategyId().intValue());
            return ResponseEntity
                    .ok(Map.of("success", true, "data", trades));
        } catch (Exception ex) {
            return ResponseEntity
                    .internalServerError()
                    .body(Map.of("success", false, "message", "获取交易历史失败: " + ex.getMessage()));
        }
    }
}
