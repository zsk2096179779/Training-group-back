package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.service.TradingService;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ExtendWith(MockitoExtension.class)
class TradingControllerTest {

    @Mock
    private TradingService tradingService;

    @InjectMocks
    private TradingController tradingController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradingController)
                .setControllerAdvice(new com.example.advisor_backend.exception.GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 支持Java 8时间类型
    }

    @Test
    void testListOrders_Success() throws Exception {
        // Given
        List<OrderResponseDTO> orderList = Arrays.asList(createOrderResponseDTO());
        PageResult<OrderResponseDTO> pageResult = new PageResult<>(orderList, 1L);
        
        when(tradingService.listOrders(anyInt(), anyInt(), anyString(), anyLong())).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/trading/orders")
                        .param("page", "1")
                        .param("size", "10")
                        .param("status", "BUY")
                        .param("strategyId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.items[0].orderId").value(1));

        verify(tradingService).listOrders(anyInt(), anyInt(), anyString(), anyLong());
    }

    @Test
    void testSubmitOrders_Success() throws Exception {
        // Given
        OrderRequestDTO request = createOrderRequestDTO();
        List<OrderResponseDTO> response = Arrays.asList(createOrderResponseDTO());
        
        when(tradingService.submitBatchOrder(any(OrderRequestDTO.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/trading/orders")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].orderId").value(1));

        verify(tradingService).submitBatchOrder(any(OrderRequestDTO.class));
    }

    @Test
    void testCancelOrder_Success() throws Exception {
        // Given
        doNothing().when(tradingService).cancelOrder(anyLong());

        // When & Then
        mockMvc.perform(delete("/api/trading/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(tradingService).cancelOrder(anyLong());
    }

    @Test
    void testFetchErrorOrders_Success() throws Exception {
        // Given
        List<OrderResponseDTO> errorOrders = Arrays.asList(createOrderResponseDTO());
        PageResult<OrderResponseDTO> pageResult = new PageResult<>(errorOrders, 1L);
        
        when(tradingService.fetchErrorOrders(anyInt(), anyInt())).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/trading/orders/errors")
                        .param("type", "error")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").value(1));

        verify(tradingService).fetchErrorOrders(anyInt(), anyInt());
    }

    @Test
    void testFetchErrorOrders_InvalidType() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/trading/orders/errors")
                        .param("type", "invalid")
                        .param("page", "1")
                        .param("limit", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(tradingService, never()).fetchErrorOrders(anyInt(), anyInt());
    }

    @Test
    void testFixError_Success() throws Exception {
        // Given
        ErrorFixDTO request = new ErrorFixDTO();
        request.setOrderId(1L);
        
        ErrorOrderDTO response = new ErrorOrderDTO();
        response.setOrderId(1L);
        response.setResolved(true);
        
        when(tradingService.fixError(any(ErrorFixDTO.class))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/trading/errors/fix")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.resolved").value(true));

        verify(tradingService).fixError(any(ErrorFixDTO.class));
    }

    @Test
    void testListFills_Success() throws Exception {
        // Given
        List<FillOrderDTO> fillOrders = Arrays.asList(createFillOrderDTO());
        PageResult<FillOrderDTO> pageResult = new PageResult<>(fillOrders, 1L);
        
        when(tradingService.listFills(1, 10)).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/trading/fills")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").value(1));

        verify(tradingService).listFills(1, 10);
    }

    @Test
    void testDoRebalance_Success() throws Exception {
        // Given
        RebalancePayload request = createRebalancePayload();
        doNothing().when(tradingService).doRebalance(any(RebalancePayload.class));

        // When & Then
        mockMvc.perform(post("/api/trading/rebalance")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tradingService).doRebalance(any(RebalancePayload.class));
    }

    @Test
    void testExecuteOrder_Success() throws Exception {
        // Given
        doNothing().when(tradingService).executeOrder(1L);

        // When & Then
        mockMvc.perform(post("/api/trading/orders/execute")
                        .content("1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tradingService).executeOrder(1L);
    }

    @Test
    void testRejectOrder_Success() throws Exception {
        // Given
        doNothing().when(tradingService).rejectOrder(1L);

        // When & Then
        mockMvc.perform(post("/api/trading/orders/reject")
                        .content("1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tradingService).rejectOrder(1L);
    }

    @Test
    void testListDeliveryNotes_Success() throws Exception {
        // Given
        List<DeliveryNoteDTO> deliveryNotes = Arrays.asList(createDeliveryNoteDTO());
        PageResult<DeliveryNoteDTO> pageResult = new PageResult<>(deliveryNotes, 1L);
        
        when(tradingService.listDeliveryNotes(1, 10)).thenReturn(pageResult);

        // When & Then
        mockMvc.perform(get("/api/trading/delivery-notes")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").value(1));

        verify(tradingService).listDeliveryNotes(1, 10);
    }

    @Test
    void testReplaceErrorOrder_Success() throws Exception {
        // Given
        ReplaceErrorOrderDTO request = new ReplaceErrorOrderDTO();
        request.setOrderId(1L);
        request.setNewFundId(2L);
        
        doNothing().when(tradingService).replaceErrorOrder(any(ReplaceErrorOrderDTO.class));

        // When & Then
        mockMvc.perform(post("/api/trading/error/replace")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tradingService).replaceErrorOrder(any(ReplaceErrorOrderDTO.class));
    }

    @Test
    void testFetchFunds_Success() throws Exception {
        // Given
        List<FundDTO> funds = Arrays.asList(createFundDTO());
        when(tradingService.fetchFunds(1, 10, "测试")).thenReturn(funds);

        // When & Then
        mockMvc.perform(get("/api/trading/fund-research/list")
                        .param("page", "1")
                        .param("limit", "10")
                        .param("nameFilter", "测试")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(tradingService).fetchFunds(1, 10, "测试");
    }

    @Test
    void testFetchAccountList_Success() throws Exception {
        // Given
        List<AccountDTO> accounts = Arrays.asList(new AccountDTO(1L, "测试账户"));
        when(tradingService.fetchAccountList("测试")).thenReturn(accounts);

        // When & Then
        mockMvc.perform(get("/api/trading/account-rebalance/accounts")
                        .param("search", "测试")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].accountId").value(1));

        verify(tradingService).fetchAccountList("测试");
    }

    @Test
    void testFetchAccountDetail_Success() throws Exception {
        // Given
        AccountDetailDTO accountDetail = createAccountDetailDTO();
        when(tradingService.fetchAccountDetail(1L)).thenReturn(accountDetail);

        // When & Then
        mockMvc.perform(get("/api/trading/account-rebalance/detail")
                        .param("accountId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(1));

        verify(tradingService).fetchAccountDetail(1L);
    }

    @Test
    void testSubmitAccountRebalance_Success() throws Exception {
        // Given
        AccountRebalancePayload request = createAccountRebalancePayload();
        doNothing().when(tradingService).submitAccountRebalance(any(AccountRebalancePayload.class));

        // When & Then
        mockMvc.perform(post("/api/trading/account-rebalance")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(tradingService).submitAccountRebalance(any(AccountRebalancePayload.class));
    }

    // Helper methods to create test data
    private OrderRequestDTO createOrderRequestDTO() {
        OrderRequestDTO dto = new OrderRequestDTO();
        dto.setAccountId(1L);
        dto.setFundId(1L);
        dto.setAction("BUY");
        dto.setAmount(1000.0);
        dto.setStrategyId(1L);
        dto.setType("BUY");
        dto.setOffset(0);
        dto.setLimit(10);
        
        OrderDetailDTO detail = new OrderDetailDTO();
        detail.setId(1L);
        detail.setAccountId("1");
        detail.setFundId("1");
        detail.setOldRatio(new BigDecimal("0.5"));
        detail.setNewRatio(new BigDecimal("0.6"));
        dto.setDetails(Arrays.asList(detail));
        
        return dto;
    }

    private OrderResponseDTO createOrderResponseDTO() {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(1L);
        dto.setStatus("PENDING");
        dto.setAmount(1000.0);
        dto.setFundName("测试基金");
        dto.setAction("BUY");
        dto.setCreateTime("2024-01-01 10:00:00");
        dto.setQuantity(new BigDecimal("100"));
        dto.setFund("1");
        dto.setAccount("1");
        return dto;
    }

    private FillOrderDTO createFillOrderDTO() {
        FillOrderDTO dto = new FillOrderDTO();
        dto.setFillId(1L);
        dto.setOrderId(1L);
        dto.setFilledAmount(1000.0);
        dto.setStatus("FILLED");
        dto.setFilledDate("2024-01-01 10:00:00");
        return dto;
    }

    private DeliveryNoteDTO createDeliveryNoteDTO() {
        DeliveryNoteDTO dto = new DeliveryNoteDTO();
        dto.setDeliveryId(1L);
        dto.setOrderId(1L);
        dto.setDeliveryStatus("DELIVERED");
        dto.setDeliveryDate("2024-01-01 10:00:00");
        return dto;
    }

    private FundDTO createFundDTO() {
        FundDTO dto = new FundDTO();
        dto.setId(1L);
        dto.setCode("000001");
        dto.setName("测试基金");
        dto.setCompanyName("测试公司");
        return dto;
    }

    private AccountDetailDTO createAccountDetailDTO() {
        AccountDetailDTO dto = new AccountDetailDTO();
        dto.setAccountId(1L);
        dto.setFunds(Collections.emptyList());
        return dto;
    }

    private RebalancePayload createRebalancePayload() {
        RebalancePayload payload = new RebalancePayload();
        payload.setAccountId(1L);
        
        RebalancePayload.FundRebalance fundRebalance = new RebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));
        
        return payload;
    }

    private AccountRebalancePayload createAccountRebalancePayload() {
        AccountRebalancePayload payload = new AccountRebalancePayload();
        payload.setAccountId(1L);
        
        AccountRebalancePayload.FundRebalance fundRebalance = new AccountRebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));
        
        return payload;
    }
} 