package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.OrderCartDto;
import com.ssu.commerce.order.dto.response.OrderCartResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface OrderCartResponseDtoMapper {
    OrderCartResponseDtoMapper INSTANCE = Mappers.getMapper(OrderCartResponseDtoMapper.class);
    
    default List<OrderCartResponseDto> mapToList(List<OrderCartDto> orderCartDtoList) {
        return orderCartDtoList.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    OrderCartResponseDto map(OrderCartDto orderCartDto);
}
