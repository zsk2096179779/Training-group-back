// src/main/java/com/example/advisor_backend/service/DataSourceService.java
package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.DataSourceConfig;
import com.example.advisor_backend.model.entity.RunScriptRequest;
import com.example.advisor_backend.model.entity.ScriptExecutionResult;

import java.util.List;

public interface DataSourceService {
    int addConfig(DataSourceConfig config);
    int updateConfig(DataSourceConfig config);
    int deleteConfig(Long id);
    DataSourceConfig getConfig(Long id);
    List<DataSourceConfig> getAllConfigs();

    /** 根据数据源配置ID拉取外部数据并导入FundFactor表 */
    int importToFundFactor(Long id);

    /** 执行用户脚本并返回执行日志 */
    ScriptExecutionResult runScript(RunScriptRequest request);
}
