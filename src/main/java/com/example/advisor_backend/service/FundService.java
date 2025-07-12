// src/main/java/com/example/advisor_backend/service/FundService.java
package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.*;

import java.util.List;

public interface FundService {
    PageResult<FundResponse> listFunds(FundFilterRequest req);
    List<LabelResponse> listAllLabels();
    FundProfileResponse getProfile(String code);
}
