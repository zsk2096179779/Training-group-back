package com.example.advisor_backend.Server;

import com.example.advisor_backend.Repository.DetailRepository;
import com.example.advisor_backend.Repository.UserRepository;
import com.example.advisor_backend.bean.Detail;
import com.example.advisor_backend.bean.Strategy;
import com.example.advisor_backend.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailServe {
    @Autowired
    private DetailRepository detailRepository;

    public Detail getDetailsById(Integer id) {
        // 根据用户ID查询关联策略
        return detailRepository.findByStrategyId(id);
    }
}
