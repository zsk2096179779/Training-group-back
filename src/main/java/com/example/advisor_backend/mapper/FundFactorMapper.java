package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.FactorDto;
import com.example.advisor_backend.model.entity.FundFactor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface FundFactorMapper {

    List<FundFactor> selectByNodeId( Long nodeId);
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
    List<FundFactor> selectByIds(@Param("ids") List<Long> ids);

    void insertBaseFactors(@Param("derivedFactorId") Long derivedFactorId, @Param("list") List<FactorDto.BaseFactorComponent> baseFactors);
    void deleteBaseFactorsByDerivedId(@Param("derivedFactorId") Long derivedFactorId);

    List<FundFactor> selectNormalFactors();

}