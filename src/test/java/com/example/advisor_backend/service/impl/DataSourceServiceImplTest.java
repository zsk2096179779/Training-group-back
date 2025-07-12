package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.mapper.DataSourceConfigMapper;
import com.example.advisor_backend.model.entity.DataSourceConfig;
import com.example.advisor_backend.service.ServiceImpl.DataSourceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataSourceServiceImplTest {
    @Mock
    private DataSourceConfigMapper dataSourceConfigMapper;
    @InjectMocks
    private DataSourceServiceImpl dataSourceService;

    @Test
    void getAllConfigs() {
        when(dataSourceConfigMapper.selectAll()).thenReturn(Arrays.asList(new DataSourceConfig(), new DataSourceConfig()));
        List<DataSourceConfig> result = dataSourceService.getAllConfigs();
        assertEquals(2, result.size());
    }

    @Test
    void getConfig() {
        DataSourceConfig config = new DataSourceConfig();
        when(dataSourceConfigMapper.selectById(1L)).thenReturn(config);
        DataSourceConfig result = dataSourceService.getConfig(1L);
        assertNotNull(result);
    }

    @Test
    void addConfig() {
        DataSourceConfig config = new DataSourceConfig();
        when(dataSourceConfigMapper.insert(config)).thenReturn(1);
        int result = dataSourceService.addConfig(config);
        assertEquals(1, result);
    }

    @Test
    void updateConfig() {
        DataSourceConfig config = new DataSourceConfig();
        when(dataSourceConfigMapper.update(config)).thenReturn(1);
        int result = dataSourceService.updateConfig(config);
        assertEquals(1, result);
    }

    @Test
    void deleteConfig() {
        when(dataSourceConfigMapper.deleteById(1L)).thenReturn(1);
        int result = dataSourceService.deleteConfig(1L);
        assertEquals(1, result);
    }
} 