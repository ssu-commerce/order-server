package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.GetOrderResponseParamDto;
import com.ssu.commerce.order.dto.response.GetOrderResponseDto;
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

    GetOrderResponseDto map(GetOrderResponseParamDto dto);
}
