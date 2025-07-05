package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.ComboCreateRequest;
import com.example.advisor_backend.model.entity.Combo;
import com.example.advisor_backend.model.entity.Fund;

import java.util.List;

public interface ComboService {
    List<Combo> list();
    List<Integer> getFundId(Long id);
    List<Fund> getFunds(List<Integer> ids);
    List<Fund> getFundsAll();
    void createCombo(ComboCreateRequest req);
}
