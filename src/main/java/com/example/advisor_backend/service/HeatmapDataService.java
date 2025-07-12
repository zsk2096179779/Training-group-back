package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.HeatmapData;

import java.util.List;

public interface HeatmapDataService {
    List<HeatmapData> getHeatmapDataById(Integer id);
}
