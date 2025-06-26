package com.example.advisor_backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FactorTreeSceneMapper {
    @Select("SELECT DISTINCT tree_type AS value FROM factor_tree_node")
    List<String> selectAllTreeTypes();
} 