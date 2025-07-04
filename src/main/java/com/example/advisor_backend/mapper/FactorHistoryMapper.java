package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.FactorHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface FactorHistoryMapper {
    @Select("SELECT * FROM factor_history WHERE factor_id = #{factorId} ORDER BY date ASC")
    List<FactorHistory> selectByFactorId(@Param("factorId") Integer factorId);

    @Select("SELECT * FROM factor_history WHERE factor_id = #{factorId} AND date >= #{start} AND date <= #{end} ORDER BY date ASC")
    List<FactorHistory> selectByFactorIdAndDateRange(
        @Param("factorId") Integer factorId,
        @Param("start") String start,
        @Param("end") String end
    );
} 