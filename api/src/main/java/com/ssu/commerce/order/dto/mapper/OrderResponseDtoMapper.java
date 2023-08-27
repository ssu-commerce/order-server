package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.OrderResponseDto;
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
public interface OrderResponseDtoMapper {
    OrderResponseDtoMapper INSTANCE = Mappers.getMapper(OrderResponseDtoMapper.class);

    OrderResponseDto map(String orderId);
}
