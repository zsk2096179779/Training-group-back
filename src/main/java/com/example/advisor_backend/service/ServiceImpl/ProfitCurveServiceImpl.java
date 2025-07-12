package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.Repository.ProfitCurveRepository;
import com.example.advisor_backend.model.entity.ProfitCurve;
import com.example.advisor_backend.service.ProfitCurveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfitCurveServiceImpl implements ProfitCurveService {
    @Autowired
    private ProfitCurveRepository profitCurveRepository;
    @Override
    public List<ProfitCurve> getProfitCurveById(Integer id) {
        // 根据ID查询关联策略
        return profitCurveRepository.findByStrategyId(id);
    }
}
