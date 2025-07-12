package com.example.advisor_backend.controller;

import com.example.advisor_backend.service.DerivedFactorService;
import com.example.advisor_backend.model.dto.CreateDerivedFactorRequest;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class DerivedFactorControllerTest {
    @Mock
    private DerivedFactorService derivedFactorService;
    @InjectMocks
    private DerivedFactorController derivedFactorController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(derivedFactorController)
                .setControllerAdvice(new com.example.advisor_backend.exception.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testCreateDerivedFactor() throws Exception {
        CreateDerivedFactorRequest request = new CreateDerivedFactorRequest();
        request.setName("测试衍生因子");
        request.setDescription("这是一个测试衍生因子");
        
        when(derivedFactorService.createDerivedFactor(any(CreateDerivedFactorRequest.class))).thenReturn(1L);
        
        mockMvc.perform(post("/api/derived-factor/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }

    @Test
    void testCreateDerivedFactorWithInvalidInput() throws Exception {
        CreateDerivedFactorRequest request = new CreateDerivedFactorRequest();
        // 设置无效数据，缺少必要字段
        
        mockMvc.perform(post("/api/derived-factor/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.msg").value(org.hamcrest.Matchers.containsString("不能为空")));
    }
} 