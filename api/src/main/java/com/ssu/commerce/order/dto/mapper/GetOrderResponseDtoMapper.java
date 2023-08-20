package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.GetOrderResponseDto;
import com.ssu.commerce.order.dto.response.OrderWithItemsDto;
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
public interface GetOrderResponseDtoMapper {
    GetOrderResponseDtoMapper INSTANCE = Mappers.getMapper(GetOrderResponseDtoMapper.class);

    GetOrderResponseDto map(OrderWithItemsDto dto);
}
