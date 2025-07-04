package com.example.fundadvisorserver.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.example.fundadvisorserver.repository.UserAgreementStatusRepository;
import com.example.fundadvisorserver.entity.UserAgreementStatus;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/agreement")
public class AgreementController {
    @Autowired
    private UserAgreementStatusRepository agreementStatusRepository;

    @GetMapping("/list")
    public List<Map<String, Object>> listAgreements() {
        List<Map<String, Object>> agreements = new ArrayList<>();
        agreements.add(Map.of("id", 1, "title", "基金交易协议", "content", "协议内容..."));
        agreements.add(Map.of("id", 2, "title", "风险揭示书", "content", "风险揭示内容..."));
        return agreements;
    }

    @PostMapping("/sign")
    public Map<String, Object> signAgreements(
        @RequestHeader("userId") Long userId,
        @RequestBody Map<String, Object> params
    ) {
        Object idsObj = params.get("agreementIds");
        if (idsObj instanceof List<?>) {
            // 这里可以将签署记录写入 user_agreement_status 或单独的签署明细表
            // 示例：只更新 user_agreement_status 状态为已签署
            UserAgreementStatus status = agreementStatusRepository.findByUserId(userId)
                .orElse(new UserAgreementStatus());
            status.setUserId(userId);
            status.setStatus("已签署");
            status.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
            agreementStatusRepository.save(status);
            // 实际业务可记录 ids 到明细表
            return Map.of("msg", "签署成功", "success", true);
        } else {
            return Map.of("msg", "参数错误", "success", false);
        }
    }
} 