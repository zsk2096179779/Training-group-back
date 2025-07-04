package com.example.fundadvisorserver.controller;

import com.example.fundadvisorserver.entity.KycQuestionnaire;
import com.example.fundadvisorserver.repository.KycQuestionnaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/kyc")
public class KycController {
    @Autowired
    private KycQuestionnaireRepository kycRepo;

    // 提交KYC问卷
    @PostMapping("/submit")
    public ResponseEntity<?> submitKyc(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        String answerJson = params.get("answerJson").toString();
        KycQuestionnaire kyc = kycRepo.findByUserId(userId).orElse(new KycQuestionnaire());
        kyc.setUserId(userId);
        kyc.setAnswerJson(answerJson);
        kyc.setCompleted(true);
        kycRepo.save(kyc);
        return ResponseEntity.ok("KYC提交成功");
    }

    // 查询KYC状态
    @GetMapping("/status")
    public ResponseEntity<?> kycStatus(@RequestParam Long userId) {
        Optional<KycQuestionnaire> kyc = kycRepo.findByUserId(userId);
        boolean completed = kyc.isPresent() && Boolean.TRUE.equals(kyc.get().getCompleted());
        return ResponseEntity.ok(Map.of("completed", completed));
    }

    // 适当性匹配检查
    @GetMapping("/match")
    public ResponseEntity<?> match(@RequestParam Long userId) {
        Optional<KycQuestionnaire> kyc = kycRepo.findByUserId(userId);
        if (kyc.isPresent() && Boolean.TRUE.equals(kyc.get().getCompleted())) {
            // 这里可以根据answerJson内容做更复杂的判断
            boolean pass = true; // 假设通过
            if (pass) {
                return ResponseEntity.ok(Map.of("match", true, "msg", "适当性匹配通过"));
            } else {
                return ResponseEntity.ok(Map.of("match", false, "msg", "适当性不匹配"));
            }
        }
        return ResponseEntity.badRequest().body("未完成KYC问卷");
    }
} 