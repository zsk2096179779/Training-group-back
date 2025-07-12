package com.example.advisor_backend.service;

import com.example.advisor_backend.mapper.AccountMapper;
import com.example.advisor_backend.mapper.ErrorOrderMapper;
import com.example.advisor_backend.mapper.FillOrderMapper;
import com.example.advisor_backend.mapper.OrderMapper;
import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.*;
import com.example.advisor_backend.Repository.*;
import com.example.advisor_backend.repository.FundRepository;
import com.example.advisor_backend.service.ServiceImpl.TradingServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TradingServiceTest {

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private OrderRepository orderRepo;

    @Mock
    private ErrorOrderRepository errorRepo;

    @Mock
    private FillOrderRepository fillRepo;

    @Mock
    private com.example.advisor_backend.repository.FundRepository fundRepo;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ErrorOrderMapper errorMapper;

    @Mock
    private FillOrderMapper fillMapper;

    @InjectMocks
    private TradingServiceImpl tradingService;

    private OrderRequestDTO orderRequestDTO;
    private OrderResponseDTO orderResponseDTO;
    private FillOrder fillOrder;
    private ErrorOrder errorOrder;
    private Fund fund;

    @BeforeEach
    void setUp() {
        // 初始化测试数据
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setType("BUY");
        orderRequestDTO.setStrategyId(1L);
        orderRequestDTO.setOffset(0);
        orderRequestDTO.setLimit(10);

        OrderDetailDTO detail = new OrderDetailDTO();
        detail.setId(1L);
        detail.setAccountId("1");
        detail.setFundId("1");
        detail.setOldRatio(new BigDecimal("0.5"));
        detail.setNewRatio(new BigDecimal("0.6"));
        orderRequestDTO.setDetails(Arrays.asList(detail));

        orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId(1L);
        orderResponseDTO.setAccount("1");
        orderResponseDTO.setFund("1");
        orderResponseDTO.setQuantity(new BigDecimal("100"));
        orderResponseDTO.setCreateTime("2024-01-01 10:00:00");

        fillOrder = new FillOrder();
        fillOrder.setId(1L);
        fillOrder.setFillDate(LocalDateTime.now());
        fillOrder.setFillAmount(1000.0);

        errorOrder = new ErrorOrder();
        errorOrder.setId(1L);
        errorOrder.setOriginalOrderId(1L);
        errorOrder.setResolved(false);

        fund = new Fund();
        fund.setId(1L);
        fund.setCode("000001");
        fund.setName("测试基金");
        fund.setCompanyName("测试公司");
    }

    @Test
    void testListOrders_Success() {
        // Given
        List<OrderResponseDTO> mockList = Arrays.asList(orderResponseDTO);
        when(orderMapper.toFinList(any(OrderRequestDTO.class))).thenReturn(mockList);
        when(orderMapper.countByCondition(any(OrderRequestDTO.class))).thenReturn(1L);

        // When
        PageResult<OrderResponseDTO> result = tradingService.listOrders(1, 10, "BUY", 1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(1L, result.getTotal());
        verify(orderMapper).toFinList(any(OrderRequestDTO.class));
        verify(orderMapper).countByCondition(any(OrderRequestDTO.class));
    }

    @Test
    void testListOrders_EmptyResult() {
        // Given
        when(orderMapper.toFinList(any(OrderRequestDTO.class))).thenReturn(Collections.emptyList());
        when(orderMapper.countByCondition(any(OrderRequestDTO.class))).thenReturn(0L);

        // When
        PageResult<OrderResponseDTO> result = tradingService.listOrders(1, 10, "BUY", 1L);

        // Then
        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0L, result.getTotal());
    }

    @Test
    void testSubmitBatchOrder_Success() {
        // Given
        doNothing().when(orderMapper).toEntityList(anyList());

        // When
        List<OrderResponseDTO> result = tradingService.submitBatchOrder(orderRequestDTO);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(orderMapper).toEntityList(anyList());
    }

    @Test
    void testSubmitBatchOrder_EmptyDetails() {
        // Given
        orderRequestDTO.setDetails(Collections.emptyList());

        // When
        List<OrderResponseDTO> result = tradingService.submitBatchOrder(orderRequestDTO);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderMapper, never()).toEntityList(anyList());
    }

    @Test
    void testSubmitBatchOrder_NullDetails() {
        // Given
        orderRequestDTO.setDetails(null);

        // When
        List<OrderResponseDTO> result = tradingService.submitBatchOrder(orderRequestDTO);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(orderMapper, never()).toEntityList(anyList());
    }

    @Test
    void testCancelOrder_Success() {
        // Given
        Order order = new Order();
        order.setId(1L);
        order.setStatus("PENDING");
        when(orderRepo.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepo.save(any(Order.class))).thenReturn(order);

        // When
        tradingService.cancelOrder(1L);

        // Then
        verify(orderRepo).findById(1L);
        verify(orderRepo).save(any(Order.class));
        assertEquals("CANCELLED", order.getStatus());
    }

    @Test
    void testCancelOrder_OrderNotFound() {
        // Given
        when(orderRepo.findById(1L)).thenReturn(Optional.empty());

        // When
        tradingService.cancelOrder(1L);

        // Then
        verify(orderRepo).findById(1L);
        verify(orderRepo, never()).save(any(Order.class));
    }

    @Test
    void testFetchErrorOrders_Success() {
        // Given
        List<OrderResponseDTO> mockList = Arrays.asList(orderResponseDTO);
        when(orderMapper.toFinList(any(OrderRequestDTO.class))).thenReturn(mockList);
        when(orderMapper.countByCondition(any(OrderRequestDTO.class))).thenReturn(1L);

        // When
        PageResult<OrderResponseDTO> result = tradingService.fetchErrorOrders(1, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(1L, result.getTotal());
    }

    @Test
    void testFixError_Success() {
        // Given
        ErrorFixDTO errorFixDTO = new ErrorFixDTO();
        errorFixDTO.setOrderId(1L);
        
        when(errorRepo.findById(1L)).thenReturn(Optional.of(errorOrder));
        when(errorRepo.save(any(ErrorOrder.class))).thenReturn(errorOrder);
        when(errorMapper.toDto(any(ErrorOrder.class))).thenReturn(new ErrorOrderDTO());

        // When
        ErrorOrderDTO result = tradingService.fixError(errorFixDTO);

        // Then
        assertNotNull(result);
        verify(errorRepo).findById(1L);
        verify(errorRepo).save(any(ErrorOrder.class));
        verify(errorMapper).toDto(any(ErrorOrder.class));
    }

    @Test
    void testFixError_OrderNotFound() {
        // Given
        ErrorFixDTO errorFixDTO = new ErrorFixDTO();
        errorFixDTO.setOrderId(1L);
        
        when(errorRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> tradingService.fixError(errorFixDTO));
        verify(errorRepo).findById(1L);
        verify(errorRepo, never()).save(any(ErrorOrder.class));
    }

    @Test
    void testListFills_Success() {
        // Given
        List<FillOrder> fillOrders = Arrays.asList(fillOrder);
        Page<FillOrder> page = new PageImpl<>(fillOrders, PageRequest.of(0, 10), 1);
        when(fillRepo.findAll(any(Pageable.class))).thenReturn(page);
        when(fillMapper.toDtoList(anyList())).thenReturn(Arrays.asList(new FillOrderDTO()));

        // When
        PageResult<FillOrderDTO> result = tradingService.listFills(1, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getTotal());
        verify(fillRepo).findAll(any(Pageable.class));
        verify(fillMapper).toDtoList(anyList());
    }

    @Test
    void testDoRebalance_Success() {
        // Given
        RebalancePayload payload = new RebalancePayload();
        payload.setAccountId(1L);
        RebalancePayload.FundRebalance fundRebalance = new RebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));

        // When
        tradingService.doRebalance(payload);

        // Then
        // 验证方法被调用，具体实现可能需要在子类中重写
        assertDoesNotThrow(() -> tradingService.doRebalance(payload));
    }

    @Test
    void testReplaceErrorOrder_Success() {
        // Given
        ReplaceErrorOrderDTO payload = new ReplaceErrorOrderDTO();
        payload.setOrderId(1L);
        payload.setNewFundId(2L);
        
        when(errorRepo.findById(1L)).thenReturn(Optional.of(errorOrder));
        when(errorRepo.save(any(ErrorOrder.class))).thenReturn(errorOrder);

        // When
        tradingService.replaceErrorOrder(payload);

        // Then
        verify(errorRepo).findById(1L);
        verify(errorRepo).save(any(ErrorOrder.class));
        assertEquals(2L, errorOrder.getFundId());
    }

    @Test
    void testReplaceErrorOrder_OrderNotFound() {
        // Given
        ReplaceErrorOrderDTO payload = new ReplaceErrorOrderDTO();
        payload.setOrderId(1L);
        payload.setNewFundId(2L);
        
        when(errorRepo.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> tradingService.replaceErrorOrder(payload));
        verify(errorRepo).findById(1L);
        verify(errorRepo, never()).save(any(ErrorOrder.class));
    }

    @Test
    void testFetchFunds_Success() {
        // Given
        List<Fund> funds = Arrays.asList(fund);
        when(fundRepo.findByNameContainingAndCompanyNameContaining(anyString(), anyString()))
                .thenReturn(funds);

        // When
        List<FundDTO> result = tradingService.fetchFunds(1, 10, "测试");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(fundRepo).findByNameContainingAndCompanyNameContaining("测试", "测试");
    }

    @Test
    void testFetchAccountList_Success() {
        // Given
        List<AccountDTO> mockAccounts = Arrays.asList(new AccountDTO(1L, "测试账户"));
        when(accountMapper.findAccountListBySearch(anyString())).thenReturn(mockAccounts);

        // When
        List<AccountDTO> result = tradingService.fetchAccountList("测试");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(accountMapper).findAccountListBySearch("测试");
    }

    @Test
    void testFetchAccountDetail_Success() {
        // Given
        AccountDetailDTO mockDetail = new AccountDetailDTO();
        when(accountMapper.findAccountById(1L)).thenReturn(mockDetail);

        // When
        AccountDetailDTO result = tradingService.fetchAccountDetail(1L);

        // Then
        assertNotNull(result);
        verify(accountMapper).findAccountById(1L);
    }

    @Test
    void testSubmitAccountRebalance_Success() {
        // Given
        AccountRebalancePayload payload = new AccountRebalancePayload();
        payload.setAccountId(1L);
        AccountRebalancePayload.FundRebalance fundRebalance = new AccountRebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));

        // When
        tradingService.submitAccountRebalance(payload);

        // Then
        // 验证方法被调用，具体实现可能需要在子类中重写
        assertDoesNotThrow(() -> tradingService.submitAccountRebalance(payload));
    }

    @Test
    void testExecuteOrder_Success() {
        // When
        tradingService.executeOrder(1L);

        // Then
        // 验证方法被调用，具体实现可能需要在子类中重写
        assertDoesNotThrow(() -> tradingService.executeOrder(1L));
    }

    @Test
    void testRejectOrder_Success() {
        // When
        tradingService.rejectOrder(1L);

        // Then
        // 验证方法被调用，具体实现可能需要在子类中重写
        assertDoesNotThrow(() -> tradingService.rejectOrder(1L));
    }
} 