package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.RegisterBookToCartParamDto;
import com.ssu.commerce.order.dto.request.RegisterBookToCartRequestDto;
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
public interface RegisterBookToCartParamDtoMapper {
    RegisterBookToCartParamDtoMapper INSTANCE = Mappers.getMapper(RegisterBookToCartParamDtoMapper.class);

    RegisterBookToCartParamDto map(RegisterBookToCartRequestDto requestDto);
}
