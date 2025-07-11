package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.entity.DataSourceConfig;
import com.example.advisor_backend.model.dto.ApiResponse;
import com.example.advisor_backend.model.entity.RunScriptRequest;
import com.example.advisor_backend.model.entity.ScriptExecutionResult;
import com.example.advisor_backend.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/datasource")
@CrossOrigin
@PreAuthorize("hasRole('ADMIN')")
public class DataSourceController {
    @Autowired
    private DataSourceService dataSourceService;

    @PostMapping
    public ApiResponse<Long> addConfig(@RequestBody DataSourceConfig config) {
        dataSourceService.addConfig(config);
        return ApiResponse.ok(config.getId());
    }

    @PutMapping
    public ApiResponse<String> updateConfig(@RequestBody DataSourceConfig config) {
        dataSourceService.updateConfig(config);
        return ApiResponse.ok("更新成功");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteConfig(@PathVariable Long id) {
        dataSourceService.deleteConfig(id);
        return ApiResponse.ok("删除成功");
    }

    @GetMapping("/{id}")
    public ApiResponse<DataSourceConfig> getConfig(@PathVariable Long id) {
        return ApiResponse.ok(dataSourceService.getConfig(id));
    }

    @GetMapping
    public ApiResponse<List<DataSourceConfig>> getAllConfigs() {
        return ApiResponse.ok(dataSourceService.getAllConfigs());
    }

    @PostMapping("/run-script")
    public ApiResponse<ScriptExecutionResult> runScript(@RequestBody RunScriptRequest request) {
        ScriptExecutionResult result = dataSourceService.runScript(request);
        return ApiResponse.ok(result);
    }

    @PostMapping("/import/{id}")
    public ApiResponse<Integer> importToFundFactor(@PathVariable Long id) {
        int count = dataSourceService.importToFundFactor(id);
        return ApiResponse.ok(count);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<DataSourceConfig> list() {
        return dataSourceService.getAllConfigs();
    }
} 