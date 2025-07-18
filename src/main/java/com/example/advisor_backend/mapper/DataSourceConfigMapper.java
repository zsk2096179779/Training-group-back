package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.DataSourceConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DataSourceConfigMapper {
    
    int insert(DataSourceConfig config);
    int update(DataSourceConfig config);
    int deleteById(Long id);
    DataSourceConfig selectById(Long id);
    List<DataSourceConfig> selectAll();
} 