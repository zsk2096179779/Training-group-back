package com.example.advisor_backend.service;

import com.example.advisor_backend.model.entity.TradeHistory;

import java.util.List;

public interface TradeHistoryService {
    List<TradeHistory> getTradeHistoryById(Integer id);
}
