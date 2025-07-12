// src/main/java/com/example/advisor_backend/controller/FundController.java
package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.service.FundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/funds")
public class FundController {

    private final FundService fundService;

    public FundController(FundService fundService) {
        this.fundService = fundService;
    }

    /** 获取所有标签 */
    @GetMapping("/labels")
    public ResponseEntity<ApiResponse<List<LabelResponse>>> listLabels() {
        return ResponseEntity.ok(ApiResponse.ok(fundService.listAllLabels()));
    }

    /** 分页查询基金 */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResult<FundResponse>>> listFunds(
            @ModelAttribute@Valid FundFilterRequest req
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(fundService.listFunds(req))
        );
    }

    @GetMapping("/{code}/profile")
    public ApiResponse<FundProfileResponse> profile(@PathVariable String code) {
        return ApiResponse.ok(fundService.getProfile(code));

    }


}
