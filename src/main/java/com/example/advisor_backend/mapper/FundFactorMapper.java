package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.entity.FundFactor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FundFactorMapper {
    // 查询所有因子
    List<FundFactor> selectAll();
    // 根据ID查询
    FundFactor selectById(Long id);
    // 新增因子
    int insert(FundFactor factor);
    // 更新因子
    int update(FundFactor factor);
    // 删除因子
    int deleteById(Long id);
}