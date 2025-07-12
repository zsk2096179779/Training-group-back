package com.example.advisor_backend.mapper;

import com.example.advisor_backend.model.dto.OrderDetailDTO;
import com.example.advisor_backend.model.dto.OrderRequestDTO;
import com.example.advisor_backend.model.dto.OrderResponseDTO;
import com.example.advisor_backend.model.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderResponseDTO> toFinList(OrderRequestDTO req);
    long countByCondition(OrderRequestDTO req);
    void toEntityList(@Param("list")List<OrderDetailDTO> list);
    List<OrderResponseDTO> toDtoList(List<Order> entities);
}
