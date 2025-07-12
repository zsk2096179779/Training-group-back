// src/main/java/com/example/advisor_backend/controller/StrategyMonitoringController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.StrategyIdRequest;
import com.example.advisor_backend.model.entity.HeatmapData;
import com.example.advisor_backend.model.entity.MonitorMetrics;
import com.example.advisor_backend.model.entity.ProfitCurve;
import com.example.advisor_backend.model.entity.RiskEvent;
import com.example.advisor_backend.service.ServiceImpl.HeatmapDataServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.MonitorMetricsServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.ProfitCurveServiceImpl;
import com.example.advisor_backend.service.ServiceImpl.RiskEventServiceImpl;
import com.example.advisor_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/strategy-monitoring")
public class StrategyMonitoringController {

    @Autowired
    private UserService userService;

    @Autowired
    private MonitorMetricsServiceImpl monitorMetricsService;

    @Autowired
    private RiskEventServiceImpl riskEventService;

    @Autowired
    private ProfitCurveServiceImpl profitCurveService;

    @Autowired
    private HeatmapDataServiceImpl heatmapDataService;

    /**
     * 获取策略监控指标
     * POST /api/strategy-monitoring/metrics
     * Body: { "strategyId": 123 }
     */
    @PostMapping("/metrics")
    public ResponseEntity<?> getMonitoringMetrics(
            Principal principal,
            @RequestBody StrategyIdRequest req
    ) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            MonitorMetrics metrics = monitorMetricsService
                    .getMonitoeMetricsById(req.getStrategyId());
            return ResponseEntity.ok(Map.of("success", true, "data", metrics));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "获取监控指标失败: " + ex.getMessage()));
        }
    }

    /**
     * 获取风险事件（预警）
     * POST /api/strategy-monitoring/warnings
     * Body: { "strategyId": 123 }
     */
    @PostMapping("/warnings")
    public ResponseEntity<?> getMonitoringWarnings(
            Principal principal,
            @RequestBody StrategyIdRequest req
    ) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            List<RiskEvent> events = riskEventService
                    .getRiskEventById(req.getStrategyId());
            return ResponseEntity.ok(Map.of("success", true, "data", events));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "获取风险事件失败: " + ex.getMessage()));
        }
    }

    /**
     * 获取收益曲线
     * POST /api/strategy-monitoring/profit-curve
     * Body: { "strategyId": 123 }
     */
    @PostMapping("/profit-curve")
    public ResponseEntity<?> getMonitoringProfitCurve(
            Principal principal,
            @RequestBody StrategyIdRequest req
    ) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            List<ProfitCurve> curves = profitCurveService
                    .getProfitCurveById(req.getStrategyId());
            return ResponseEntity.ok(Map.of("success", true, "data", curves));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "获取收益曲线失败: " + ex.getMessage()));
        }
    }

    /**
     * 获取风险热力图
     * POST /api/strategy-monitoring/heatmap
     * Body: { "strategyId": 123 }
     */
    @PostMapping("/heatmap")
    public ResponseEntity<?> getMonitoringHeatmap(
            Principal principal,
            @RequestBody StrategyIdRequest req
    ) {
        if (req.getStrategyId() == null) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("success", false, "message", "缺少 strategyId 参数"));
        }
        try {
            List<HeatmapData> heatmap = heatmapDataService
                    .getHeatmapDataById(req.getStrategyId());
            return ResponseEntity.ok(Map.of("success", true, "data", heatmap));
        } catch (Exception ex) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("success", false, "message", "获取热力图失败: " + ex.getMessage()));
        }
    }
}
