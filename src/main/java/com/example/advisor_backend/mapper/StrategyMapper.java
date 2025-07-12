package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.entity.Strategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StrategyMapper {

    // 查询策略列表并支持分页
    @Select("<script>" +
            "SELECT id, create_time, gain, name, owner, status, type " +
            "FROM strategy " +
            "WHERE owner = 1 " +
            "<if test='nameFilter != null and nameFilter != \"\"'>" +
            "AND name LIKE CONCAT('%', #{nameFilter}, '%') " +
            "</if>" +
            "<if test='statusFilter != null and statusFilter != \"all\"'>" +
            "AND status = stop " +
            "</if>" +
            "ORDER BY create_time DESC " +
            "LIMIT #{offset}, #{limit}" +
            "</script>")
    List<Strategy> findAll(
            @Param("ownerId") Long ownerId,
            @Param("nameFilter") String nameFilter,
            @Param("statusFilter") String statusFilter,
            @Param("offset") int offset,
            @Param("limit") int limit
    );

    // 查询符合条件的策略总数
    @Select("<script>" +
            "SELECT COUNT(*) " +
            "FROM strategy " +
            "WHERE owner = #{ownerId} " +
            "<if test='nameFilter != null and nameFilter != \"\"'>" +
            "AND name LIKE CONCAT('%', #{nameFilter}, '%') " +
            "</if>" +
            "<if test='statusFilter != null and statusFilter != \"all\"'>" +
            "AND status = #{statusFilter} " +
            "</if>" +
            "</script>")
    long countStrategies(
            @Param("ownerId") Long ownerId,
            @Param("nameFilter") String nameFilter,
            @Param("statusFilter") String statusFilter
    );
}