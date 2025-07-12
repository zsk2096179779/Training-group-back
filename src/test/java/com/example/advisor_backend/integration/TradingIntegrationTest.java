package com.example.advisor_backend.integration;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.*;
import com.example.advisor_backend.Repository.*;
import com.example.advisor_backend.service.TradingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * 交易管理系统集成测试
 * 测试完整的业务流程，包括订单管理、错误处理、调仓等
 */
@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ActiveProfiles("test")
@Transactional
class TradingIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TradingService tradingService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ErrorOrderRepository errorOrderRepository;

    @Autowired
    private FillOrderRepository fillOrderRepository;

    @Autowired
    private com.example.advisor_backend.repository.FundRepository fundRepository;

    @Autowired
    private AccountRepository accountRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
        setupTestData();
    }

    /**
     * 设置测试数据
     */
    private void setupTestData() {
        // 创建测试基金
        Fund fund = new Fund();
        fund.setCode("000001");
        fund.setName("测试基金");
        fund.setCompanyName("测试公司");
        fund.setNav(new BigDecimal("1.2345"));
        fundRepository.save(fund);

        // 创建测试账户
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountName("测试账户");
        accountRepository.save(account);

        // 创建测试订单
        Order order = new Order();
        order.setStrategyId(1L);
        order.setFundCode("000001");
        order.setAmount(1000.0);
        order.setStatus("PENDING");
        order.setOldRatio(0.5);
        order.setNewRatio(0.6);
        order.setCreateTime(LocalDateTime.now());
        order.setType("BUY");
        order.setAccountId(1L);
        order.setFundId(1L);
        orderRepository.save(order);
    }


    @Test
    void testListOrders() throws Exception {
        // Given
        // 测试数据已在setUp中创建

        // When & Then
        mockMvc.perform(get("/api/trading/orders")
                        .param("page", "1")
                        .param("size", "10")
                        .param("status", "BUY")
                        .param("strategyId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.total").exists());
    }


    @Test
    void testCancelOrder() throws Exception {
        // Given
        Order order = orderRepository.findAll().get(0);

        // When & Then
        mockMvc.perform(delete("/api/trading/orders/" + order.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // 验证订单状态已更新
        Order cancelledOrder = orderRepository.findById(order.getId()).orElse(null);
        assertNotNull(cancelledOrder);
        assertEquals("CANCELLED", cancelledOrder.getStatus());
    }


    @Test
    void testRebalance() throws Exception {
        // Given
        RebalancePayload request = createTestRebalancePayload();

        // When & Then
        mockMvc.perform(post("/api/trading/rebalance")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testFundResearch() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/trading/fund-research/list")
                        .param("page", "1")
                        .param("limit", "10")
                        .param("nameFilter", "测试")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testAccountRebalance() throws Exception {
        // Given
        AccountRebalancePayload request = createTestAccountRebalancePayload();

        // When & Then
        mockMvc.perform(post("/api/trading/account-rebalance")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testOrderExecution() throws Exception {
        // Given
        Order order = orderRepository.findAll().get(0);

        // When & Then - 测试执行订单
        mockMvc.perform(post("/api/trading/orders/execute")
                        .content(objectMapper.writeValueAsString(order.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // When & Then - 测试拒绝订单
        mockMvc.perform(post("/api/trading/orders/reject")
                        .content(objectMapper.writeValueAsString(order.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    @Test
    void testReplaceErrorOrder() throws Exception {
        // Given
        ErrorOrder errorOrder = createTestErrorOrder();
        ReplaceErrorOrderDTO request = new ReplaceErrorOrderDTO();
        request.setOrderId(errorOrder.getId());
        request.setNewFundId(2L);

        // When & Then
        mockMvc.perform(post("/api/trading/error/replace")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Helper methods to create test data
    private OrderRequestDTO createTestOrderRequest() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setAccountId(1L);
        request.setFundId(1L);
        request.setAction("BUY");
        request.setAmount(1000.0);
        request.setStrategyId(1L);
        request.setType("BUY");
        request.setOffset(0);
        request.setLimit(10);

        OrderDetailDTO detail = new OrderDetailDTO();
        detail.setId(1L);
        detail.setAccountId("1");
        detail.setFundId("1");
        detail.setOldRatio(new BigDecimal("0.5"));
        detail.setNewRatio(new BigDecimal("0.6"));
        request.setDetails(Arrays.asList(detail));

        return request;
    }

    private ErrorOrder createTestErrorOrder() {
        ErrorOrder errorOrder = new ErrorOrder();
        errorOrder.setOriginalOrderId(1L);
        errorOrder.setFundCode("000001");
        errorOrder.setErrorMsg("测试错误");
        errorOrder.setResolved(false);
        errorOrder.setCreatedAt(LocalDateTime.now());
        errorOrder.setFundId(1L);
        return errorOrderRepository.save(errorOrder);
    }

    private FillOrder createTestFillOrder() {
        FillOrder fillOrder = new FillOrder();
        fillOrder.setOrderId(1L);
        fillOrder.setFundCode("000001");
        fillOrder.setFillAmount(1000.0);
        fillOrder.setFillDate(LocalDateTime.now());
        return fillOrderRepository.save(fillOrder);
    }

    private RebalancePayload createTestRebalancePayload() {
        RebalancePayload payload = new RebalancePayload();
        payload.setAccountId(1L);
        payload.setStrategyId(1L);

        RebalancePayload.FundRebalance fundRebalance = new RebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setCurrentRatio(0.5);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));

        return payload;
    }

    private AccountRebalancePayload createTestAccountRebalancePayload() {
        AccountRebalancePayload payload = new AccountRebalancePayload();
        payload.setAccountId(1L);

        AccountRebalancePayload.FundRebalance fundRebalance = new AccountRebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));

        return payload;
    }



    @Test
    void testDataPersistence() {
        // 测试数据持久化
        assertDoesNotThrow(() -> {
            // 验证基金数据
            List<Fund> funds = fundRepository.findAll();
            assertFalse(funds.isEmpty());

            // 验证账户数据
            List<Account> accounts = accountRepository.findAll();
            assertFalse(accounts.isEmpty());

            // 验证订单数据
            List<Order> orders = orderRepository.findAll();
            assertFalse(orders.isEmpty());
        });
    }

    @Test
    void testErrorHandling() {
        // 测试错误处理
        assertDoesNotThrow(() -> {
            // 测试不存在的订单ID
            tradingService.cancelOrder(999999L);

            // 测试空的订单请求
            OrderRequestDTO emptyRequest = new OrderRequestDTO();
            List<OrderResponseDTO> result = tradingService.submitBatchOrder(emptyRequest);
            assertNotNull(result);
        });
    }
} 