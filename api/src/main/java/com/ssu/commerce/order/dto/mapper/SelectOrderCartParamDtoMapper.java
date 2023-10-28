package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.param.SelectOrderCartDto;
import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface SelectOrderCartParamDtoMapper {
    SelectOrderCartParamDtoMapper INSTANCE = Mappers.getMapper(SelectOrderCartParamDtoMapper.class);

    @Mapping(source = "orderCartItemList.bookId", target = "bookId")
    @Mapping(source = "orderCartItemList.addedAt", target = "addedAt")
    SelectCartItemParamDto map(SelectOrderCartDto dto);

}
