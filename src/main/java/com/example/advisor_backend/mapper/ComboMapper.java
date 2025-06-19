package com.example.advisor_backend.mapper;

import com.example.advisor_backend.entity.Combo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComboMapper {
    // 查询产品（含关联基金）
    @Select("SELECT * FROM products")
    List<Combo> list();
}
