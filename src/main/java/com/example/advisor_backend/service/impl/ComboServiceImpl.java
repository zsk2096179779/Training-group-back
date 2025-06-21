package com.example.advisor_backend.service.impl;

import com.example.advisor_backend.entity.Combo;
import com.example.advisor_backend.entity.Fund;
import com.example.advisor_backend.mapper.ComboMapper;
import com.example.advisor_backend.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}