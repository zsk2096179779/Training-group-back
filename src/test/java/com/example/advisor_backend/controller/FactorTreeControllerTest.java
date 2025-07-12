package com.example.advisor_backend.controller;

import com.example.advisor_backend.service.FactorTreeService;
import com.example.advisor_backend.model.entity.FactorTreeNode;
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
class FactorTreeControllerTest {
    @Mock
    private FactorTreeService factorTreeService;
    @InjectMocks
    private FactorTreeController factorTreeController;
    
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(factorTreeController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testGetAllTrees() throws Exception {
        List<FactorTreeNode> trees = Arrays.asList(new FactorTreeNode(), new FactorTreeNode());
        when(factorTreeService.getAllNodes()).thenReturn(trees);
        
        mockMvc.perform(get("/api/factor-tree"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void testGetTreeById() throws Exception {
        FactorTreeNode tree = new FactorTreeNode();
        tree.setName("测试树");
        when(factorTreeService.getChildren(1L)).thenReturn(Arrays.asList(tree));
        
        mockMvc.perform(get("/api/factor-tree/children/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0));
    }
} 