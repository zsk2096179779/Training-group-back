package com.example.advisor_backend.controller;

import com.example.advisor_backend.Server.HeatmapDataServe;
import com.example.advisor_backend.Server.MonitorMetricsServe;
import com.example.advisor_backend.Server.ProfitCurveServe;
import com.example.advisor_backend.Server.RiskEventServe;
import com.example.advisor_backend.bean.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
public class Strategy_Monitoring {
    @Autowired
    private MonitorMetricsServe monitorMetricsServe;
    @Autowired
    private RiskEventServe riskEventServe;
    @Autowired
    private ProfitCurveServe profitCurveServe;
    @Autowired
    private HeatmapDataServe heatmapDataServe;
    @PostMapping("/strategy-monitoring/Metrics")
    public ResponseEntity<?> GetMonitoryMetrics(@RequestBody User user) {
        try {
            System.out.println(1);
            MonitorMetrics monitorMetrics=monitorMetricsServe.getMonitoeMetricsById(user.getId());
            System.out.println(monitorMetrics);
            return ResponseEntity.ok(monitorMetrics);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-monitoring/Warnings")
    public ResponseEntity<?> GetMonitoryWarn(@RequestBody User user) {
        try {
            System.out.println(2);
            List<RiskEvent> riskEvent=riskEventServe.getRiskEventById(user.getId());
            System.out.println(riskEvent);
            return ResponseEntity.ok(riskEvent);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-monitoring/ProfitCurve")
    public ResponseEntity<?> GetMonitoryProfitCurve(@RequestBody User user) {
        try {
            System.out.println(3);
            List<ProfitCurve> profitCurves=profitCurveServe.getProfitCurveById(user.getId());
            System.out.println(profitCurves);
            return ResponseEntity.ok(profitCurves);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
    @PostMapping("/strategy-monitoring/Heatmap")
    public ResponseEntity<?> GetMonitoryHeatmap(@RequestBody User user) {
        try {
            System.out.println(4);
            List<HeatmapData> heatmapData=heatmapDataServe.getHeatmapDataById(user.getId());
            System.out.println(heatmapData);
            return ResponseEntity.ok(heatmapData);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to retrieve strategies: " + e.getMessage());
        }
    }
}
