package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.query.SelectOrderCartListParamDto;
import com.ssu.commerce.order.dto.param.query.SelectOrderListParamDto;
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
public interface SelectOrderCartListParamDtoMapper {
    SelectOrderCartListParamDtoMapper INSTANCE = Mappers.getMapper(SelectOrderCartListParamDtoMapper.class);

    SelectOrderCartListParamDto map(GetOrderCartListParamDto paramDto);
}
