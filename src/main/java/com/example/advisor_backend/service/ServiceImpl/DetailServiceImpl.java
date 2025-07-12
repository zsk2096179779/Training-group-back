package com.example.advisor_backend.service.ServiceImpl;

import com.example.advisor_backend.repository.DetailRepository;
import com.example.advisor_backend.model.entity.Detail;
import com.example.advisor_backend.service.DetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailServiceImpl implements DetailService {
    @Autowired
    private DetailRepository detailRepository;

    @Override
    public Detail getDetailsById(Integer id) {
        // 根据用户ID查询关联策略
        return detailRepository.findByStrategyId(id);
    }
}
