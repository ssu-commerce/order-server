package com.ssu.commerce.order.dto.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Pageable;


@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface GetOrderListParamMapper {
    GetOrderListParamMapper INSTANCE = Mappers.getMapper(GetOrderListParamMapper.class);

    GetOrderListParamDto map(String userId, Pageable pageable);
}
