package com.example.advisor_backend.Repository;

import com.example.advisor_backend.bean.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StrategyRepository extends JpaRepository<Strategy, Integer> {

    // 通过owner字段查询策略
    List<Strategy> findByOwner(Integer ownerId);
    //通过Id查询
    Strategy findByid(Integer strategyId);

}
