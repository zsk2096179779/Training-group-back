package com.example.advisor_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.advisor_backend.model.dto.entity.FactorDerivation;

@Mapper
public interface DerivedFactorRelationMapper {
    int insertRelation(@Param("derivedId") Long derivedId, @Param("baseId") Long baseId, @Param("weight") Double weight);
    int insert(FactorDerivation derivation);
} 