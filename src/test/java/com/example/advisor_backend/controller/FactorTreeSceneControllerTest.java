package com.example.advisor_backend.controller;

import com.example.advisor_backend.mapper.FactorTreeSceneMapper;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FactorTreeSceneControllerTest {
    @Mock
    private FactorTreeSceneMapper factorTreeSceneMapper;
    
    @InjectMocks
    private FactorTreeSceneController factorTreeSceneController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(factorTreeSceneController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testGetAllScenes() throws Exception {
        // Given
        List<String> treeTypes = Arrays.asList("股票型", "债券型", "混合型");
        when(factorTreeSceneMapper.selectAllTreeTypes()).thenReturn(treeTypes);
        
        // When & Then
        mockMvc.perform(get("/api/factor-tree/scenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].value").value("股票型"))
                .andExpect(jsonPath("$.data[0].label").value("股票型"))
                .andExpect(jsonPath("$.data[1].value").value("债券型"))
                .andExpect(jsonPath("$.data[1].label").value("债券型"))
                .andExpect(jsonPath("$.data[2].value").value("混合型"))
                .andExpect(jsonPath("$.data[2].label").value("混合型"));
        
        verify(factorTreeSceneMapper, times(1)).selectAllTreeTypes();
    }

    @Test
    void testGetSceneById() throws Exception {
        // Given
        List<String> treeTypes = Arrays.asList("股票型");
        when(factorTreeSceneMapper.selectAllTreeTypes()).thenReturn(treeTypes);
        
        // When & Then
        mockMvc.perform(get("/api/factor-tree/scenes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].value").value("股票型"))
                .andExpect(jsonPath("$.data[0].label").value("股票型"));
        
        verify(factorTreeSceneMapper, times(1)).selectAllTreeTypes();
    }
} 