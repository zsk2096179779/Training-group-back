package com.example.advisor_backend.controller;

import com.example.advisor_backend.Server.*;
import com.example.advisor_backend.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Strategy_Detail {
    @Autowired
    private UserServe userServe;
    @Autowired
    private DetailServe detailService;
    @Autowired
    private ProfitChartServe profitChartServe;
    @Autowired
    private HoldingServe holdingServe;
    @Autowired
    private TradeHistoryServe tradeHistoryServe;
    @PostMapping("/strategy-management/Detail")
    public ResponseEntity<?> GetStrategyDetail(@RequestBody User user) {
        String username = user.getName();
        if (username == null || username.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("用户名不能为空");
        }
        username = username.trim();
        int lishi=user.getId();
        user = userServe.getUserByName(username);
        // 检查用户是否存在
        if (user == null) {
            return ResponseEntity.status(404).body("用户不存在");
        }
        try {
            Detail detail= detailService.getDetailsById(lishi);
            if(detail.getStrategy().getOwner()==user.getId()) {
                System.out.println(detail);
                return ResponseEntity.ok(detail);
            }
            else
            {
                return ResponseEntity.status(400).body("无权限");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-management/Chart")
    public ResponseEntity<?> GetStrategyChart(@RequestBody User user) {
        try {
            ProfitChart profitChart= profitChartServe.getChartById(user.getId());
            System.out.println(profitChart);
            return ResponseEntity.ok(profitChart);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-management/Holding")
    public ResponseEntity<?> GetStrategyHolding(@RequestBody User user) {
        try {
            List<Holding> holding = holdingServe.getHoldingById(user.getId());
            System.out.println(holding);
            return ResponseEntity.ok(holding);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-management/TradeHistory")
    public ResponseEntity<?> GetStrategyTradeHistory(@RequestBody User user) {
        try {
            List<TradeHistory> tradeHistory = tradeHistoryServe.getTradeHistoryById(user.getId());
            System.out.println(tradeHistory);
            return ResponseEntity.ok(tradeHistory);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
}
