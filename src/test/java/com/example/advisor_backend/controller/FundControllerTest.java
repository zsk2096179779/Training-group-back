package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.service.FundService;
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
import org.springframework.web.bind.MethodArgumentNotValidException;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FundControllerTest {

    @Mock
    private FundService fundService;

    @InjectMocks
    private FundController fundController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(fundController)
                .setControllerAdvice(new com.example.advisor_backend.exception.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void listFunds_BasicQuery() throws Exception {
        // Given
        List<FundResponse> fundList = Arrays.asList(
                createFundResponse("000001", "华夏成长", 1.2345),
                createFundResponse("000002", "华夏回报", 1.3456)
        );
        PageResult<FundResponse> pageResult = new PageResult<>(fundList, 2L);
        
        when(fundService.listFunds(any(FundFilterRequest.class))).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/funds")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.items[0].code").value("000001"))
                .andExpect(jsonPath("$.data.items[1].code").value("000002"));

        verify(fundService, times(1)).listFunds(any(FundFilterRequest.class));
    }

    @Test
    void listFunds_InvalidPage() throws Exception {
        // Test invalid page = 0
        mockMvc.perform(get("/api/funds")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(fundService, never()).listFunds(any(FundFilterRequest.class));

        // Test invalid page = -1
        mockMvc.perform(get("/api/funds")
                        .param("page", "-1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(fundService, never()).listFunds(any(FundFilterRequest.class));
    }

    @Test
    void listFunds_InvalidSize() throws Exception {
        // Test invalid size = 0
        mockMvc.perform(get("/api/funds")
                        .param("page", "1")
                        .param("size", "0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(fundService, never()).listFunds(any(FundFilterRequest.class));

        // Test invalid size = -1
        mockMvc.perform(get("/api/funds")
                        .param("page", "1")
                        .param("size", "-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(fundService, never()).listFunds(any(FundFilterRequest.class));
    }

    @Test
    void listFunds_WithFilters() throws Exception {
        // Given
        List<FundResponse> fundList = Arrays.asList(
                createFundResponse("000001", "华夏成长", 1.2345)
        );
        PageResult<FundResponse> pageResult = new PageResult<>(fundList, 1L);
        
        when(fundService.listFunds(any(FundFilterRequest.class))).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/funds")
                        .param("page", "1")
                        .param("size", "10")
                        .param("code", "华夏")
                        .param("companyName", "华夏基金")
                        .param("managerName", "张三")
                        .param("labels", "1", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.items").isArray())
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.items[0].code").value("000001"));

        verify(fundService, times(1)).listFunds(any(FundFilterRequest.class));
    }

    @Test
    void getFundProfile_Success() throws Exception {
        // Given
        FundProfileResponse profile = createFundProfileResponse("000001", "华夏成长");
        when(fundService.getProfile("000001")).thenReturn(profile);

        // When & Then
        mockMvc.perform(get("/api/funds/000001/profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data.code").value("000001"))
                .andExpect(jsonPath("$.data.name").value("华夏成长"))
                .andExpect(jsonPath("$.data.companyName").value("华夏基金"))
                .andExpect(jsonPath("$.data.managerName").value("张三"));

        verify(fundService, times(1)).getProfile("000001");
    }

    @Test
    void getFundProfile_EmptyCode() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/funds//profile")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(fundService, never()).getProfile(any());
    }

    @Test
    void listLabels_Success() throws Exception {
        // Given
        List<LabelResponse> labels = Arrays.asList(
                new LabelResponse(1L, "股票型"),
                new LabelResponse(2L, "债券型"),
                new LabelResponse(3L, "混合型")
        );
        when(fundService.listAllLabels()).thenReturn(labels);

        // When & Then
        mockMvc.perform(get("/api/funds/labels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("股票型"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("债券型"))
                .andExpect(jsonPath("$.data[2].id").value(3))
                .andExpect(jsonPath("$.data[2].name").value("混合型"));

        verify(fundService, times(1)).listAllLabels();
    }

    @Test
    void listLabels_EmptyList() throws Exception {
        // Given
        when(fundService.listAllLabels()).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/funds/labels")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());

        verify(fundService, times(1)).listAllLabels();
    }

    // Helper methods
    private FundResponse createFundResponse(String code, String name, double nav) {
        FundResponse fund = new FundResponse();
        fund.setCode(code);
        fund.setName(name);
        fund.setNav(BigDecimal.valueOf(nav));
        fund.setRisk(3.0);
        fund.setCompanyName("华夏基金");
        fund.setLabels(Arrays.asList(
                new LabelResponse(1L, "股票型"),
                new LabelResponse(2L, "成长型")
        ));
        return fund;
    }

    private FundProfileResponse createFundProfileResponse(String code, String name) {
        FundProfileResponse profile = new FundProfileResponse();
        profile.setCode(code);
        profile.setName(name);
        profile.setCompanyName("华夏基金");
        profile.setManagerName("张三");
        profile.setNav(BigDecimal.valueOf(1.2345));
        profile.setRisk(3.0);
        profile.setNavHistory(Arrays.asList(
                createTimeValue("2023-01-01", 1.2000),
                createTimeValue("2023-01-02", 1.2100)
        ));
        profile.setMetrics(Arrays.asList(
                new FundProfileResponse.Metric("年化收益", 0.15),
                new FundProfileResponse.Metric("夏普率", 1.2),
                new FundProfileResponse.Metric("最大回撤", -0.08)
        ));
        profile.setHoldings(Arrays.asList(
                createHolding("贵州茅台", 0.05),
                createHolding("腾讯控股", 0.04)
        ));
        profile.setAttribution(Arrays.asList(
                createAttribution("股票选择", 0.03),
                createAttribution("行业配置", 0.02)
        ));
        profile.setAnnouncements(Arrays.asList(
                createAnnouncement("2023-01-01", "基金分红公告"),
                createAnnouncement("2023-01-02", "基金经理变更公告")
        ));
        return profile;
    }

    private FundProfileResponse.TimeValue createTimeValue(String date, double value) {
        FundProfileResponse.TimeValue tv = new FundProfileResponse.TimeValue();
        tv.setDate(date);
        tv.setValue(BigDecimal.valueOf(value));
        return tv;
    }

    private FundProfileResponse.Holding createHolding(String name, double percent) {
        FundProfileResponse.Holding holding = new FundProfileResponse.Holding();
        holding.setName(name);
        holding.setPercent(BigDecimal.valueOf(percent));
        return holding;
    }

    private FundProfileResponse.Attribution createAttribution(String category, double value) {
        FundProfileResponse.Attribution attribution = new FundProfileResponse.Attribution();
        attribution.setCategory(category);
        attribution.setValue(value);
        return attribution;
    }

    private FundProfileResponse.Announcement createAnnouncement(String date, String title) {
        FundProfileResponse.Announcement announcement = new FundProfileResponse.Announcement();
        announcement.setDate(LocalDate.parse(date));
        announcement.setTitle(title);
        return announcement;
    }
}

 