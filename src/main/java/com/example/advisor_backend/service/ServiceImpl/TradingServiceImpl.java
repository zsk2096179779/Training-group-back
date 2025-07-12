package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.mapper.AccountMapper;
import com.example.advisor_backend.mapper.ErrorOrderMapper;
import com.example.advisor_backend.mapper.FillOrderMapper;
import com.example.advisor_backend.mapper.OrderMapper;
import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.model.entity.ErrorOrder;
import com.example.advisor_backend.model.entity.FillOrder;
import com.example.advisor_backend.model.entity.Fund;
import com.example.advisor_backend.Repository.*;
import com.example.advisor_backend.repository.FundRepository;
import com.example.advisor_backend.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TradingServiceImpl implements TradingService {

    @Autowired private AccountRepository accountRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private ErrorOrderRepository errorRepo;
    @Autowired private FillOrderRepository fillRepo;
    @Autowired private AccountMapper  accountMapper;

    @Autowired private OrderMapper orderMapper;
    @Autowired private ErrorOrderMapper errorMapper;
    @Autowired private FillOrderMapper fillMapper;

    @Autowired private FundRepository fundRepo;

    // 1. 分页查询订单列表
    @Override
    public PageResult<OrderResponseDTO> listOrders(int page, int size, String status, Long strategyId) {
        OrderRequestDTO req = new OrderRequestDTO();
        req.setType(status);  // 订单状态
        req.setStrategyId(strategyId);  // 策略ID
        req.setOffset((page - 1) * size);  // 设置偏移量
        req.setLimit(size);  // 设置每页的数量

        // 使用 MyBatis 查询获取订单列表
        List<OrderResponseDTO> list = orderMapper.toFinList(req);
        // 获取总记录数
        long total = orderMapper.countByCondition(req);
        return new PageResult<>(list, total);  // 返回分页结果
    }

    // 2. 批量提交建仓／调仓／赎回指令
    @Override
    @Transactional
    public List<OrderResponseDTO> submitBatchOrder(OrderRequestDTO req) {
        List<OrderDetailDTO> details = req.getDetails();  // 获取订单明细
        if (details == null || details.isEmpty()) {
            return Collections.emptyList();  // 如果没有订单明细，则返回空列表
        }

        // 批量转换 DTO -> Entity 并插入
        orderMapper.toEntityList(details);  // 批量插入订单数据

        // 返回插入后的订单数据
        return details.stream()
                .map(d -> {
                    OrderResponseDTO dto = new OrderResponseDTO();
                    dto.setOrderId(d.getId());  // 设置订单ID
                    dto.setAccount(d.getAccountId());
                    dto.setFund(d.getFundId());
                    dto.setQuantity(d.getOldRatio());
                    dto.setCreateTime(String.valueOf(d.getNewRatio()));
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 3. 取消单笔订单
    @Override
    public void cancelOrder(Long orderId) {
        orderRepo.findById(orderId).ifPresent(order -> {
            order.setStatus("CANCELLED");  // 更新订单状态
            orderRepo.save(order);  // 保存修改后的订单
        });
    }

    // 4. 获取失败订单列表 （差错列表）
    @Override
    public PageResult<OrderResponseDTO> fetchErrorOrders(int page, int limit) {
        OrderRequestDTO req = new OrderRequestDTO();
        req.setType("error");
        req.setOffset((page - 1) * limit);
        req.setLimit(limit);

        // 使用 MyBatis 查询获取错误订单
        List<OrderResponseDTO> list = orderMapper.toFinList(req);
        long total = orderMapper.countByCondition(req);
        return new PageResult<>(list, total);
    }

    // 5. 差错订单修复（替换标的 + 重下单）
    @Override
    @Transactional
    public ErrorOrderDTO fixError(ErrorFixDTO req) {
        // 查找差错订单
        ErrorOrder errorOrder = errorRepo.findById(req.getOrderId())
                .orElseThrow(() -> new RuntimeException("Error Order Not Found"));

        // 进行修复操作，假设是标的替换并标记为已解决
        errorOrder.setResolved(true);  // 标记为已解决
        errorRepo.save(errorOrder);  // 保存修改后的差错订单

        return errorMapper.toDto(errorOrder);  // 返回修复后的订单DTO
    }

    // 6. 分页查询交割单
    @Override
    public PageResult<FillOrderDTO> listFills(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("fillDate").descending());
        Page<FillOrder> fillOrders = fillRepo.findAll(pageable);
        return new PageResult<>(fillMapper.toDtoList(fillOrders.getContent()), fillOrders.getTotalElements());
    }

    // 7. 组合调仓
    @Override
    public void doRebalance(RebalancePayload payload) {
        // 执行调仓逻辑
        // 例如，更新账户的持仓比例，生成新的调仓指令等
        for (RebalancePayload.FundRebalance fund : payload.getFunds()) {
            // 更新每个基金的目标比例，假设是更新数据库中相应的记录
            updateFundRatio(payload.getAccountId(), fund.getFundId(), fund.getTargetRatio());
        }
    }

    private void updateFundRatio(Long accountId, Long fundId, Double targetRatio) {
        // 根据账户ID和基金ID更新目标比例
        // 这里你可以实现自己的更新逻辑，例如更新数据库中的数据
    }

    // 8. 替换差错订单
    @Override
    public void replaceErrorOrder(ReplaceErrorOrderDTO payload) {
        // 查找失败的订单并替换标的
        ErrorOrder errorOrder = errorRepo.findById(payload.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order Not Found"));
        errorOrder.setFundId(payload.getNewFundId());  // 更新基金ID
        errorRepo.save(errorOrder);  // 保存修改后的订单
    }

    // 9. 获取可替换的基金列表
    @Override
    public List<FundDTO> fetchFunds(int page, int limit, String nameFilter) {
        // 使用分页查询基金，按名称和公司名过滤
        List<Fund> funds = fundRepo.findByNameContainingAndCompanyNameContaining(nameFilter, nameFilter);

        // 将基金实体转换为 FundDTO
        return funds.stream()
                .map(FundDTO::new)
                .collect(Collectors.toList());
    }

    // 10. 获取账户列表（带搜索、偏离度）
    @Override
    public List<AccountDTO> fetchAccountList(String search) {
        // 查询符合搜索条件的账户列表
        return accountMapper.findAccountListBySearch(search);  // 返回账户列表
    }

    // 11. 获取单个账户的调仓详情
    @Override
    public AccountDetailDTO fetchAccountDetail(Long accountId) {
        // 查找账户调仓详情
        return accountMapper.findAccountById(accountId);  // 返回账户调仓详情
    }

    // 12. 提交账户调仓
    @Override
    public void submitAccountRebalance(AccountRebalancePayload payload) {
        // 提交账户调仓的具体操作，例如更新持仓比例
        for (AccountRebalancePayload.FundRebalance fund : payload.getFunds()) {
            // 执行每个基金的调仓操作
            updateAccountFundRatio(payload.getAccountId(), fund.getFundId(), fund.getTargetRatio());
        }
    }

    @Override
    public void executeOrder(Long orderId) {

    }

    @Override
    public void rejectOrder(Long orderId) {

    }

    @Override
    public PageResult<DeliveryNoteDTO> listDeliveryNotes(int page, int size) {
        return null;
    }

    private void updateAccountFundRatio(Long accountId, Long fundId, Double targetRatio) {
        // 执行基金的调仓操作，例如更新数据库中的持仓比例
        // 你可以实现你自己的更新逻辑
    }
}
