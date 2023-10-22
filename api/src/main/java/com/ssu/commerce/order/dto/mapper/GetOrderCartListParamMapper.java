package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.GetOrderCartListParamDto;
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
public interface GetOrderCartListParamMapper {
    GetOrderCartListParamMapper INSTANCE = Mappers.getMapper(GetOrderCartListParamMapper.class);

    GetOrderCartListParamDto map(UUID userId, Pageable pageable);

}
