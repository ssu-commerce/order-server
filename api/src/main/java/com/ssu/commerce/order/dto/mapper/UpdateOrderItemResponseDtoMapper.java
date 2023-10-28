package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.UpdateOrderItemResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface UpdateOrderItemResponseDtoMapper {
    UpdateOrderItemResponseDtoMapper INSTANCE = Mappers.getMapper(UpdateOrderItemResponseDtoMapper.class);

    UpdateOrderItemResponseDto map(UUID orderItemId);
}
