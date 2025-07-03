package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.entity.Combo;
import com.example.advisor_backend.entity.Fund;
import com.example.advisor_backend.mapper.ComboMapper;
import com.example.advisor_backend.service.ComboService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    public void createCombo(Combo combo) {

        Long comboId = generateNumericComboId();
        combo.setId(String.valueOf(comboId));

        combo.setStatus("ON_SHELF"); // 默认状态
        combo.setCreatedTime(LocalDateTime.now());
        combo.setUpdatedTime(LocalDateTime.now());

        // 2. 插入 combo
        ComboMapper.insertCombo(combo);

        // 3. 插入关联基金（默认 weight = 1 / N）
        List<Fund> funds = combo.getFunds();
        if (funds != null && !funds.isEmpty()) {
            BigDecimal equalWeight = BigDecimal.ONE.divide(BigDecimal.valueOf(funds.size()), 4, RoundingMode.HALF_UP);
            for (Fund fund : funds) {
                ComboMapper.insertComboFund(String.valueOf(comboId), fund.getFundCode(), equalWeight);
            }
        }
    }

}