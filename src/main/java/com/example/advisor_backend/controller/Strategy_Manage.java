package com.example.advisor_backend.controller;

import com.example.advisor_backend.Server.UserServe;
import com.example.advisor_backend.Server.UsersStrategy;
import com.example.advisor_backend.bean.Strategy;
import com.example.advisor_backend.bean.User;
import com.example.advisor_backend.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class Strategy_Manage {
    @Autowired
    private UserServe userServe;
    @Autowired
    private UsersStrategy strategyService;
    //查找策略
    @PostMapping("/strategy-management")
    public ResponseEntity<?> CheckUser(@RequestBody User user) {
        String username = user.getName();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        username = username.trim();
        user = userServe.getUserByName(username);
        // 检查用户是否存在
        if (user == null) {
            return ResponseEntity.status(404).body("用户不存在");
        }
        try {
            List<Strategy> strategies = strategyService.getStrategiesByUser(user);
            return ResponseEntity.ok(strategies);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    //启动策略
    @PostMapping("/strategy-management/start")
    public ResponseEntity<?> startStrategy(@RequestBody Map<String, Integer> request) {
        try {
            Integer strategyId = request.get("strategyId");
            if (strategyId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "success", false,
                                "message", "缺少策略ID参数"
                        ));
            }
            System.out.println(strategyId);
            strategyService.startStrategy(strategyId);
            Strategy strategy = strategyService.getStrategiesById(strategyId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已启动"
                    )
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "success", false,
                            "message", "启动策略失败: " + e.getMessage()
                    ));
        }
    }
    //停止策略
    @PostMapping("/strategy-management/stop")
    public ResponseEntity<?> stopStrategy(@RequestBody Map<String, Integer> request) {
        try {
            Integer strategyId = request.get("strategyId");
            if (strategyId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "success", false,
                                "message", "缺少策略ID参数"
                        ));
            }
            strategyService.stopStrategy(strategyId);
            // 获取更新后的策略状态，返回给前端
            Strategy strategy = strategyService.getStrategiesById(strategyId);
            if (strategy == null) {
                return ResponseEntity.internalServerError()
                        .body(Map.of(
                                "success", false,
                                "message", "策略停止成功，但获取策略信息失败"
                        ));
            }

            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已停止",
                    "strategy", Map.of(
                            "id", strategyId,
                            "status", strategy.getStatus()
                    )
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "success", false,
                            "message", "停止策略失败: " + e.getMessage()
                    ));
        }
    }
    //删除策略
    @PostMapping("/strategy-management/delete")
    public ResponseEntity<?> deleteStrategy(@RequestBody Map<String, Integer> request) {
        try {
            Integer strategyId = request.get("strategyId");
            if (strategyId == null) {
                return ResponseEntity.badRequest()
                        .body(Map.of(
                                "success", false,
                                "message", "缺少策略ID参数"
                        ));
            }
            strategyService.deleteStrategy(strategyId);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "策略已删除"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of(
                            "success", false,
                            "message", "删除策略失败: " + e.getMessage()
                    ));
        }
    }
}
