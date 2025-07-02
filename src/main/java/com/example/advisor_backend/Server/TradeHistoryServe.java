package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.HoldingRepository;
import com.example.advisor_backend.Repository.TradeHistoryRepository;
import com.example.advisor_backend.bean.Holding;
import com.example.advisor_backend.bean.TradeHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeHistoryServe {
    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;
    public List<TradeHistory> getTradeHistoryById(Integer id) {
        // 根据ID查询关联策略
        return tradeHistoryRepository.findByStrategyId(id);
    }
}
