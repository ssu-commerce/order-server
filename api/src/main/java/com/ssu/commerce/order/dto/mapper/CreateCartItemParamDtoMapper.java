package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.CreateCartItemParamDto;
import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
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
public interface CreateCartItemParamDtoMapper {
    CreateCartItemParamDtoMapper INSTANCE = Mappers.getMapper(CreateCartItemParamDtoMapper.class);

    CreateCartItemParamDto map(CreateCartItemRequestDto requestDto);
}
