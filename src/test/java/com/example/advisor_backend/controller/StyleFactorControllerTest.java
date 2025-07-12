package com.example.advisor_backend.controller;

import com.example.advisor_backend.service.StyleFactorService;
import com.example.advisor_backend.model.entity.StyleFactor;
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
class StyleFactorControllerTest {
    @Mock
    private StyleFactorService styleFactorService;
    @InjectMocks
    private StyleFactorController styleFactorController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(styleFactorController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testGetAllStyleFactors() throws Exception {
        List<StyleFactor> factors = Arrays.asList(new StyleFactor(), new StyleFactor());
        when(styleFactorService.getAll()).thenReturn(factors);
        
        mockMvc.perform(get("/api/style-factors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testCreateStyleFactorWithValidInput() throws Exception {
        StyleFactor factor = new StyleFactor();
        factor.setName("Test Factor");
        factor.setWeights("[{\"factor\":\"value1\",\"weight\":\"50.0\"},{\"factor\":\"value2\",\"weight\":\"50.0\"}]"); // Valid JSON with sum = 100
        
        doAnswer(invocation -> {
            StyleFactor savedFactor = invocation.getArgument(0);
            savedFactor.setId(1L);
            return null;
        }).when(styleFactorService).create(any(StyleFactor.class));
        
        mockMvc.perform(post("/api/style-factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value(1));
    }

    @Test
    void testCreateStyleFactorWithInvalidInput() throws Exception {
        StyleFactor factor = new StyleFactor();
        factor.setName("Test Factor");
        factor.setWeights("invalid json string"); // Invalid JSON that will cause parsing error
        
        mockMvc.perform(post("/api/style-factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(org.hamcrest.Matchers.containsString("weights 字段格式错误")));
    }

    @Test
    void testCreateStyleFactorWithInvalidWeightSum() throws Exception {
        StyleFactor factor = new StyleFactor();
        factor.setName("Test Factor");
        factor.setWeights("[{\"factor\":\"value\",\"weight\":\"50.0\"}]"); // Sum not equal to 100
        
        mockMvc.perform(post("/api/style-factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(org.hamcrest.Matchers.containsString("权重总和必须为100")));
    }

    @Test
    void testDeleteStyleFactor() throws Exception {
        doNothing().when(styleFactorService).delete(1L);
        
        mockMvc.perform(delete("/api/style-factors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
} 