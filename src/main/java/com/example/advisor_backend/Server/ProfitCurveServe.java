package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.ProfitCurveRepository;
import com.example.advisor_backend.Repository.RiskEventRepository;
import com.example.advisor_backend.bean.ProfitCurve;
import com.example.advisor_backend.bean.RiskEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitCurveServe {
    @Autowired
    private ProfitCurveRepository profitCurveRepository;
    public List<ProfitCurve> getProfitCurveById(Integer id) {
        // 根据ID查询关联策略
        return profitCurveRepository.findByStrategyId(id);
    }
}
