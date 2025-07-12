package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.ErrorOrderDTO;
import com.example.advisor_backend.model.entity.ErrorOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ErrorOrderMapper {
    ErrorOrderDTO toDto(ErrorOrder entity);
    ErrorOrder toEntity(ErrorOrderDTO dto);
    List<ErrorOrderDTO> toDtoList(List<ErrorOrder> entities);
}