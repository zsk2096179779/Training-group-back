package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.FillOrderDTO;
import com.example.advisor_backend.model.entity.FillOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FillOrderMapper {
    FillOrderDTO toDto(FillOrder entity);
    List<FillOrderDTO> toDtoList(List<FillOrder> entities);
}