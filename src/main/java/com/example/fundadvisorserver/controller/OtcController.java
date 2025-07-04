package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.UserOtcStatus;
import com.example.fundadvisorserver.repository.UserOtcStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/otc")
public class OtcController {
    @Autowired
    private UserOtcStatusRepository otcStatusRepository;

    @PostMapping("/open")
    public Map<String, Object> openOtc(
        @RequestHeader("userId") Long userId,
        @RequestBody Map<String, Object> params
    ) {
        String name = (String) params.get("name");
        String idCard = (String) params.get("idCard");
        String mobile = (String) params.get("mobile");
        UserOtcStatus status = otcStatusRepository.findByUserId(userId)
            .orElse(new UserOtcStatus());
        status.setUserId(userId);
        status.setStatus("已开户");
        status.setName(name);
        status.setIdCard(idCard);
        status.setMobile(mobile);
        status.setUpdatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
        otcStatusRepository.save(status);
        return Map.of("msg", "开户成功");
    }
} 