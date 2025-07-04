package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.model.dto.FactorHistoryResponse;
import com.example.advisor_backend.model.entity.FundFactor;
import com.example.advisor_backend.model.entity.FactorHistory;
import com.example.advisor_backend.service.FundFactorService;
import com.example.advisor_backend.mapper.FactorHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/factors")
@CrossOrigin
public class FactorHistoryController {

    @Autowired
    private FundFactorService fundFactorService;
    @Autowired
    private FactorHistoryMapper factorHistoryMapper;

    @GetMapping("/history")
    public ApiResponse<?> getHistory(
            @RequestParam(required = false) Long nodeId,
            @RequestParam(required = false) Long factorId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        if (start == null) start = "1900-01-01";
        if (end == null) end = "2100-12-31";

        // 优先 factorId 查询，忽略 nodeId
        if (factorId != null) {
            FundFactor factor = fundFactorService.getFactorById(factorId);
            if (factor == null) {
                return ApiResponse.error(404, "因子不存在");
            }
            List<FactorHistory> historyList = factorHistoryMapper.selectByFactorIdAndDateRange(factorId.intValue(), start, end);
            List<String> dates = historyList.stream().map(h -> h.getDate().toString()).collect(java.util.stream.Collectors.toList());
            List<Double> values = historyList.stream().map(h -> h.getValue().doubleValue()).collect(java.util.stream.Collectors.toList());
            FactorHistoryResponse resp = new FactorHistoryResponse(factor.getName(), dates, values);
            return ApiResponse.ok(resp);
        }

        // 只传 nodeId，返回该节点下所有因子的曲线
        if (nodeId != null) {
            List<FundFactor> factors = fundFactorService.getFactorsByNode(nodeId);
            List<FactorHistoryResponse> result = new java.util.ArrayList<>();
            for (FundFactor factor : factors) {
                List<FactorHistory> historyList = factorHistoryMapper.selectByFactorIdAndDateRange(factor.getId().intValue(), start, end);
                List<String> dates = historyList.stream().map(h -> h.getDate().toString()).collect(java.util.stream.Collectors.toList());
                List<Double> values = historyList.stream().map(h -> h.getValue().doubleValue()).collect(java.util.stream.Collectors.toList());
                result.add(new FactorHistoryResponse(factor.getName(), dates, values));
            }
            return ApiResponse.ok(result);
        }

        // 两个都不传，报错
        return ApiResponse.error(400, "请传入 nodeId 或 factorId");
    }
} 