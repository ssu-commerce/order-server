package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.CartItemParamDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface CartItemParamDtoMapper {
    CartItemParamDtoMapper INSTANCE = Mappers.getMapper(CartItemParamDtoMapper.class);

    CartItemParamDto map(UUID userId, Pageable pageable);

}
