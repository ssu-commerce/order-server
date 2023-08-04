package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.ReturnBookResponseDto;
import org.mapstruct.factory.Mappers;

public interface ReturnBookResponseDtoMapper {
    ReturnBookResponseDtoMapper INSTANCE = Mappers.getMapper(ReturnBookResponseDtoMapper.class);

    ReturnBookResponseDto map(String orderItemId);
}
