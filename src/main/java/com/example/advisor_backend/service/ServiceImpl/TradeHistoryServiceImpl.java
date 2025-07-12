package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.repository.TradeHistoryRepository;
import com.example.advisor_backend.model.entity.TradeHistory;
import com.example.advisor_backend.service.ProfitCurveService;
import com.example.advisor_backend.service.TradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {
    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;

    @Override
    public List<TradeHistory> getTradeHistoryById(Integer id) {
        // 根据ID查询关联策略
        return tradeHistoryRepository.findByStrategyId(id);
    }
}
