package com.example.advisor_backend.controller;

import com.example.advisor_backend.service.FundFactorService;
import com.example.advisor_backend.model.entity.FundFactor;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class FundFactorControllerTest {
    @Mock
    private FundFactorService fundFactorService;
    @InjectMocks
    private FundFactorController fundFactorController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundFactorController)
                .setControllerAdvice(new com.example.advisor_backend.exception.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testGetAllFactors() throws Exception {
        List<FundFactor> factors = Arrays.asList(new FundFactor(), new FundFactor());
        when(fundFactorService.getAllFactors()).thenReturn(factors);
        
        mockMvc.perform(get("/api/factors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetFactorByIdNotFound() throws Exception {
        when(fundFactorService.getFactorById(1L)).thenReturn(null);
        
        mockMvc.perform(get("/api/factors/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg").value("因子不存在"));
    }


    
    @Test
    void testCreateFactorWithValidInput() throws Exception {
        FundFactor factor = new FundFactor();
        factor.setName("测试因子");
        factor.setDescription("这是一个测试因子");
        
        when(fundFactorService.createFactor(any(FundFactor.class))).thenReturn(true);
        
        mockMvc.perform(post("/api/factors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(factor)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").value("创建成功"));
        
        verify(fundFactorService, times(1)).createFactor(any(FundFactor.class));
    }
} 