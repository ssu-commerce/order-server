package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.SelectOrderCartParamDto;
import com.ssu.commerce.order.dto.response.OrderCartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface OrderCartResponseDtoMapper {
    OrderCartResponseDtoMapper INSTANCE = Mappers.getMapper(OrderCartResponseDtoMapper.class);

    List<OrderCartResponseDto> mapToList(List<SelectOrderCartParamDto> dto);
    OrderCartResponseDto map(SelectOrderCartParamDto orderCartDto);
}
