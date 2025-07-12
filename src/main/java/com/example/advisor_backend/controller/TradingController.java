package com.example.advisor_backend.controller;

import com.example.advisor_backend.model.dto.*;
import com.example.advisor_backend.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trading")
public class TradingController {

    @Autowired
    private TradingService tradingService;

    @GetMapping("/orders")
    public ResponseEntity<PageResult<OrderResponseDTO>> listOrders(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(name = "page",required = false) String status,
            @RequestParam(name = "size",required = false) Long strategyId
    ) {
        PageResult<OrderResponseDTO> result = tradingService.listOrders(page, size, status, strategyId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/orders")
    public ResponseEntity<List<OrderResponseDTO>> submitOrders(@RequestBody OrderRequestDTO req) {
        List<OrderResponseDTO> result = tradingService.submitBatchOrder(req);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        tradingService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders/errors")
    public ResponseEntity<PageResult<OrderResponseDTO>> fetchErrorOrders(
            @RequestParam (required = false, defaultValue = "error") String type,
            @RequestParam int page,
            @RequestParam int limit
    ) {
        if ("error".equals(type)) {
            PageResult<OrderResponseDTO> result = tradingService.fetchErrorOrders(page, limit);
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/errors/fix")
    public ResponseEntity<ErrorOrderDTO> fixError(@RequestBody ErrorFixDTO req) {
        ErrorOrderDTO result = tradingService.fixError(req);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/fills")
    public ResponseEntity<PageResult<FillOrderDTO>> listFills(
            @RequestParam int page,
            @RequestParam int size
    ) {
        PageResult<FillOrderDTO> result = tradingService.listFills(page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/rebalance")
    public ResponseEntity<Void> doRebalance(@RequestBody RebalancePayload payload) {
        tradingService.doRebalance(payload);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/execute")
    public ResponseEntity<Void> executeOrder(@RequestBody Long orderId) {
        tradingService.executeOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/reject")
    public ResponseEntity<Void> rejectOrder(@RequestBody Long orderId) {
        tradingService.rejectOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delivery-notes")
    public ResponseEntity<PageResult<DeliveryNoteDTO>> listDeliveryNotes(
            @RequestParam int page,
            @RequestParam int size
    ) {
        PageResult<DeliveryNoteDTO> result = tradingService.listDeliveryNotes(page, size);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/error/replace")
    public ResponseEntity<Void> replaceErrorOrder(@RequestBody ReplaceErrorOrderDTO payload) {
        tradingService.replaceErrorOrder(payload);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fund-research/list")
    public ResponseEntity<List<FundDTO>> fetchFunds(
            @RequestParam int page,
            @RequestParam int limit,
            @RequestParam String nameFilter
    ) {
        List<FundDTO> result = tradingService.fetchFunds(page, limit, nameFilter);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account-rebalance/accounts")
    public ResponseEntity<List<AccountDTO>> fetchAccountList(@RequestParam(defaultValue = "")  String search) {
        List<AccountDTO> result = tradingService.fetchAccountList(search);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/account-rebalance/detail")
    public ResponseEntity<AccountDetailDTO> fetchAccountDetail(@RequestParam Long accountId) {
        AccountDetailDTO result = tradingService.fetchAccountDetail(accountId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/account-rebalance")
    public ResponseEntity<Void> submitAccountRebalance(@RequestBody AccountRebalancePayload payload) {
        tradingService.submitAccountRebalance(payload);
        return ResponseEntity.ok().build();
    }
}
