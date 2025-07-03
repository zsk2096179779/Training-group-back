// src/main/java/com/example/advisor_backend/service/impl/FundStatsServiceImpl.java
package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.service.FundStatsService;
import org.springframework.stereotype.Service;

@Service
public class FundStatsServiceImpl implements FundStatsService {

    @Override
    public double getAnnualizedReturn(String code) {
        // TODO 从历史数据或第三方计算年化收益
        return 0.0;
    }

    @Override
    public double getSharpeRatio(String code) {
        // TODO 计算夏普率
        return 0.0;
    }

    @Override
    public double getMaxDrawdown(String code) {
        // TODO 计算最大回撤
        return 0.0;
    }
}
