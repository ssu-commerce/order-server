package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.OrderResponseDto;
import org.mapstruct.factory.Mappers;

public interface OrderResponseDtoMapper {
    OrderResponseDtoMapper INSTANCE = Mappers.getMapper(OrderResponseDtoMapper.class);

    OrderResponseDto map(String userId);
}
