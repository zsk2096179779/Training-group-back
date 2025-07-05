package com.example.advisor_backend.controller;

import com.example.advisor_backend.Repository.RebalanceRepository;
import com.example.advisor_backend.Server.RebalanceServe;
import com.example.advisor_backend.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class Strategy_Rebalance {
    @Autowired
    private RebalanceServe rebalanceServe;
    @PostMapping("/strategy-rebalance/Detail")
    public ResponseEntity<?> GetRebalanceDetail(@RequestBody User user) {
        try {
            RebalanceSetting rebalanceSetting=rebalanceServe.getRebalanceById(user.getId());
            System.out.println(rebalanceSetting);
            return ResponseEntity.ok(rebalanceSetting);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-rebalance/Update")
    public ResponseEntity<?> GetRebalanceUpdate(@RequestBody RebalanceSetting rebalanceSetting) {
        try {
            System.out.println(rebalanceSetting);
            rebalanceServe.save(rebalanceSetting);
            return ResponseEntity.ok(1);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-rebalance/HuiCe")
    public ResponseEntity<BacktestResponse> runBacktest(@RequestBody BacktestRequest request) {
        System.out.println("Received backtest request: " + request);

        // 使用随机数生成模拟结果
        BacktestResponse response = new BacktestResponse();
        response.setCumulativeReturn(Math.random() * 2 - 0.5); // 随机在 -0.5 到 1.5 之间
        response.setMaxDrawdown(Math.random() * 0.5); // 0% 到 50% 的回撤
        response.setSharpeRatio(Math.random() * 3); // 0 到 3 的夏普比率
        response.setTrades((int)(Math.random() * 100)); // 0 到 100 次的交易

        // 模拟回测处理时间（500ms-2s）
        try {
            long sleepTime = 500 + (long)(Math.random() * 1500);
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println(response);
        return ResponseEntity.ok(response);
    }
}
