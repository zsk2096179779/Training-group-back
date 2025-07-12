package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.ProfitCurve;

import java.util.List;

public interface ProfitCurveService {
    List<ProfitCurve> getProfitCurveById(Integer id);
}
