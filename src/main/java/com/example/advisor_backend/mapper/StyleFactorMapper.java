package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.StyleFactor;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface StyleFactorMapper {
    @Select("SELECT * FROM style_factors")
    List<StyleFactor> selectAll();

    @Insert("INSERT INTO style_factors(name, weights, create_time) VALUES(#{name}, #{weights}, NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(StyleFactor styleFactor);

    @Delete("DELETE FROM style_factors WHERE id = #{id}")
    int deleteById(Long id);
} 