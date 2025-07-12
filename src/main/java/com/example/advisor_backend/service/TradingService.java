package com.example.advisor_backend.service;

import com.example.advisor_backend.model.dto.*;

import java.util.List;

public interface TradingService {
    PageResult<OrderResponseDTO> listOrders(int page, int size, String status, Long strategyId);
    List<OrderResponseDTO> submitBatchOrder(OrderRequestDTO req);
    void cancelOrder(Long orderId);
    PageResult<OrderResponseDTO> fetchErrorOrders(int page, int limit);
    ErrorOrderDTO fixError(ErrorFixDTO req);
    PageResult<FillOrderDTO> listFills(int page, int size);
    void replaceErrorOrder(ReplaceErrorOrderDTO payload);
    void doRebalance(RebalancePayload payload);
    List<FundDTO> fetchFunds(int page, int limit, String nameFilter);
    List<AccountDTO> fetchAccountList(String search);
    AccountDetailDTO fetchAccountDetail(Long accountId);
    void submitAccountRebalance(AccountRebalancePayload payload);
    void executeOrder(Long orderId);
    void rejectOrder(Long orderId);
    PageResult<DeliveryNoteDTO> listDeliveryNotes(int page, int size);
}
