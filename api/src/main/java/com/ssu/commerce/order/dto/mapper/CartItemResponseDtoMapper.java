package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import com.ssu.commerce.order.dto.response.CartItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface CartItemResponseDtoMapper {
    CartItemResponseDtoMapper INSTANCE = Mappers.getMapper(CartItemResponseDtoMapper.class);

    CartItemResponseDto map(SelectCartItemParamDto orderCartDto);
}
