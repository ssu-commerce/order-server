package com.ssu.commerce.order.dto.mapper;

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
public interface OrderCartDtoMapper {
    OrderCartDtoMapper INSTANCE = Mappers.getMapper(OrderCartDtoMapper.class);

    //List<OrderCartDto> mapToList(List<OrderCart> orderCartList);

  //  OrderCartDto map(OrderCart orderCart);

}
