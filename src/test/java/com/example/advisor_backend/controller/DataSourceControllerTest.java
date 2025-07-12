package com.example.advisor_backend.controller;

import com.example.advisor_backend.service.DataSourceService;
import com.example.advisor_backend.model.entity.DataSourceConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DataSourceControllerTest {
    @Mock
    private DataSourceService dataSourceService;
    @InjectMocks
    private DataSourceController dataSourceController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(dataSourceController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testGetAllDataSources() throws Exception {
        List<DataSourceConfig> configs = Arrays.asList(new DataSourceConfig(), new DataSourceConfig());
        when(dataSourceService.getAllConfigs()).thenReturn(configs);
        
        mockMvc.perform(get("/api/datasource"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetDataSourceById() throws Exception {
        DataSourceConfig config = new DataSourceConfig();
        config.setName("测试数据源");
        when(dataSourceService.getConfig(1L)).thenReturn(config);
        
        mockMvc.perform(get("/api/datasource/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.name").value("测试数据源"));
    }
} 