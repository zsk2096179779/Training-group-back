package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.model.dto.ComboCreateRequest;
import com.example.advisor_backend.model.dto.FundRef;
import com.example.advisor_backend.model.entity.Combo;
import com.example.advisor_backend.mapper.ComboMapper;
import com.example.advisor_backend.model.entity.Fund;
import com.example.advisor_backend.service.ComboService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComboServiceImpl implements ComboService {
    @Autowired
    private ComboMapper ComboMapper;

    @Override
    public List<Combo> list() {

        return ComboMapper.list();

    }
    @Override
    public List<Integer> getFundId(Long id){
        return ComboMapper.getFundId(id);
    }
    @Override
    public List<Fund> getFunds(List<Integer> ids){
        return ComboMapper.getFunds(ids);
    }
    @Override
    public List<Fund> getFundsAll(){
        return ComboMapper.getFundsAll();
    }

    private long generateNumericComboId() {
        long timestamp = System.currentTimeMillis();
        int random = (int)(Math.random() * 1_000_000);
        return Long.parseLong(String.format("%d%06d", timestamp, random).substring(0, 18));
    }

    @Override
    @Transactional
    public void createCombo(ComboCreateRequest req) {
        // 1) 映射基本字段
        Combo combo = new Combo();
        combo.setName(req.getName());
        combo.setType(req.getType());
        combo.setRiskLevel(req.getRiskLevel());
        combo.setStrategy(req.getStrategy());
        combo.setSource(req.getSource());
        // isUserCreated 假设你在 DTO 中也改成 Boolean （或 String），这里也保护一下：
        combo.setIsUserCreated(
                Boolean.parseBoolean(req.getIsUserCreated() != null
                        ? req.getIsUserCreated().toString()   // 或者 Boolean → "true"/"false"
                        : "true")
        );

        // 2) 处理可能为 null 的数值字段，给个默认 0
        BigDecimal ar = req.getAnnualizedReturn() != null
                ? BigDecimal.valueOf(req.getAnnualizedReturn())
                : BigDecimal.ZERO;
        BigDecimal md = req.getMaxDrawdown() != null
                ? BigDecimal.valueOf(req.getMaxDrawdown())
                : BigDecimal.ZERO;
        combo.setAnnualizedReturn(ar);
        combo.setMaxDrawdown(md);

        combo.setMinimumInvestment(
                req.getMinimumInvestment() != null
                        ? req.getMinimumInvestment()
                        : 0
        );

        // 3) 系统字段
        String id = String.valueOf(generateNumericComboId());
        combo.setId(id);
        combo.setStatus("ON_SHELF");
        combo.setCreatedTime(LocalDateTime.now());
        combo.setUpdatedTime(LocalDateTime.now());

        // 4) 写入 combo 表
        ComboMapper.insertCombo(combo);

        // 5) 写入 combo_fund
        List<FundRef> funds = req.getFunds();
        if (funds != null && !funds.isEmpty()) {
            BigDecimal equalWeight = BigDecimal.ONE
                    .divide(BigDecimal.valueOf(funds.size()), 4, RoundingMode.HALF_UP);
            for (FundRef f : funds) {
                ComboMapper.insertComboFund(id, f.getFundCode(), equalWeight);
            }
        }
    }
}