package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.example.advisor_backend.mapper.FactorTreeSceneMapper;

@RestController
@RequestMapping("/api/factor-tree")
@CrossOrigin
public class FactorTreeSceneController {

    @Autowired
    private FactorTreeSceneMapper factorTreeSceneMapper;

    @GetMapping("/scenes")
    public ApiResponse<List<Map<String, String>>> getScenes() {
        List<String> treeTypes = factorTreeSceneMapper.selectAllTreeTypes();
        List<Map<String, String>> scenes = treeTypes.stream()
            .map(type -> Map.of("value", type, "label", type))
            .collect(java.util.stream.Collectors.toList());
        return ApiResponse.ok(scenes);
    }
} 