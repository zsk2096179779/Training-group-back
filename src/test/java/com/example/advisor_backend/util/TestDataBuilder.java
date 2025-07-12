package com.example.advisor_backend.util;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 测试数据构建器
 * 用于生成各种测试数据
 */
public class TestDataBuilder {

    /**
     * 创建测试订单请求DTO
     */
    public static OrderRequestDTO createOrderRequestDTO() {
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

    /**
     * 创建测试订单响应DTO
     */
    public static OrderResponseDTO createOrderResponseDTO() {
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

    /**
     * 创建测试基金实体
     */
    public static Fund createFund() {
        Fund fund = new Fund();
        fund.setId(1L);
        fund.setCode("000001");
        fund.setName("测试基金");
        fund.setCompanyName("测试公司");
        fund.setNav(new BigDecimal("1.2345"));
        fund.setRisk(0.15);
        fund.setManagerName("测试经理");
        fund.setInceptionDate(LocalDateTime.now());
        return fund;
    }

    /**
     * 创建测试账户实体
     */
    public static Account createAccount() {
        Account account = new Account();
        account.setAccountId(1L);
        account.setAccountName("测试账户");
        return account;
    }

    /**
     * 创建测试订单实体
     */
    public static Order createOrder() {
        Order order = new Order();
        order.setId(1L);
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
        return order;
    }

    /**
     * 创建测试错误订单实体
     */
    public static ErrorOrder createErrorOrder() {
        ErrorOrder errorOrder = new ErrorOrder();
        errorOrder.setId(1L);
        errorOrder.setOriginalOrderId(1L);
        errorOrder.setFundCode("000001");
        errorOrder.setErrorMsg("测试错误");
        errorOrder.setResolved(false);
        errorOrder.setCreatedAt(LocalDateTime.now());
        errorOrder.setFundId(1L);
        return errorOrder;
    }

    /**
     * 创建测试交割单实体
     */
    public static FillOrder createFillOrder() {
        FillOrder fillOrder = new FillOrder();
        fillOrder.setId(1L);
        fillOrder.setOrderId(1L);
        fillOrder.setFundCode("000001");
        fillOrder.setFillAmount(1000.0);
        fillOrder.setFillDate(LocalDateTime.now());
        return fillOrder;
    }

    /**
     * 创建测试基金DTO
     */
    public static FundDTO createFundDTO() {
        FundDTO dto = new FundDTO();
        dto.setId(1L);
        dto.setCode("000001");
        dto.setName("测试基金");
        dto.setCompanyName("测试公司");
        return dto;
    }

    /**
     * 创建测试账户DTO
     */
    public static AccountDTO createAccountDTO() {
        return new AccountDTO(1L, "测试账户");
    }

    /**
     * 创建测试账户详情DTO
     */
    public static AccountDetailDTO createAccountDetailDTO() {
        AccountDetailDTO dto = new AccountDetailDTO();
        dto.setAccountId(1L);
        dto.setFunds(Arrays.asList());
        return dto;
    }

    /**
     * 创建测试错误修复DTO
     */
    public static ErrorFixDTO createErrorFixDTO() {
        ErrorFixDTO dto = new ErrorFixDTO();
        dto.setOrderId(1L);
        dto.setNewFundId(2L);
        dto.setFixReason("测试修复");
        return dto;
    }

    /**
     * 创建测试错误订单DTO
     */
    public static ErrorOrderDTO createErrorOrderDTO() {
        ErrorOrderDTO dto = new ErrorOrderDTO();
        dto.setOrderId(1L);
        dto.setErrorReason("测试错误");
        dto.setOriginalFundId("000001");
        dto.setNewFundId("000002");
        dto.setResolved(false);
        return dto;
    }

    /**
     * 创建测试交割单DTO
     */
    public static FillOrderDTO createFillOrderDTO() {
        FillOrderDTO dto = new FillOrderDTO();
        dto.setFillId(1L);
        dto.setOrderId(1L);
        dto.setFilledAmount(1000.0);
        dto.setStatus("FILLED");
        dto.setFilledDate("2024-01-01 10:00:00");
        return dto;
    }

    /**
     * 创建测试交割单DTO
     */
    public static DeliveryNoteDTO createDeliveryNoteDTO() {
        DeliveryNoteDTO dto = new DeliveryNoteDTO();
        dto.setDeliveryId(1L);
        dto.setOrderId(1L);
        dto.setDeliveryStatus("DELIVERED");
        dto.setDeliveryDate("2024-01-01 10:00:00");
        return dto;
    }

    /**
     * 创建测试调仓载荷
     */
    public static RebalancePayload createRebalancePayload() {
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

    /**
     * 创建测试账户调仓载荷
     */
    public static AccountRebalancePayload createAccountRebalancePayload() {
        AccountRebalancePayload payload = new AccountRebalancePayload();
        payload.setAccountId(1L);

        AccountRebalancePayload.FundRebalance fundRebalance = new AccountRebalancePayload.FundRebalance();
        fundRebalance.setFundId(1L);
        fundRebalance.setTargetRatio(0.6);
        payload.setFunds(Arrays.asList(fundRebalance));

        return payload;
    }

    /**
     * 创建测试替换错误订单DTO
     */
    public static ReplaceErrorOrderDTO createReplaceErrorOrderDTO() {
        ReplaceErrorOrderDTO dto = new ReplaceErrorOrderDTO();
        dto.setOrderId(1L);
        dto.setNewFundId(2L);
        return dto;
    }

    /**
     * 创建测试订单列表
     */
    public static List<OrderResponseDTO> createOrderResponseList() {
        return Arrays.asList(
                createOrderResponseDTO(),
                createOrderResponseDTO()
        );
    }

    /**
     * 创建测试基金列表
     */
    public static List<FundDTO> createFundDTOList() {
        return Arrays.asList(
                createFundDTO(),
                createFundDTO()
        );
    }

    /**
     * 创建测试账户列表
     */
    public static List<AccountDTO> createAccountDTOList() {
        return Arrays.asList(
                createAccountDTO(),
                createAccountDTO()
        );
    }

    /**
     * 创建测试交割单列表
     */
    public static List<FillOrderDTO> createFillOrderDTOList() {
        return Arrays.asList(
                createFillOrderDTO(),
                createFillOrderDTO()
        );
    }

    /**
     * 创建测试交割单列表
     */
    public static List<DeliveryNoteDTO> createDeliveryNoteDTOList() {
        return Arrays.asList(
                createDeliveryNoteDTO(),
                createDeliveryNoteDTO()
        );
    }
} 