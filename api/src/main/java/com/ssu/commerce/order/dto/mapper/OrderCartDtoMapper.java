package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.OrderCartDto;
import com.ssu.commerce.order.model.OrderCart;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface OrderCartDtoMapper {
    OrderCartDtoMapper INSTANCE = Mappers.getMapper(OrderCartDtoMapper.class);

    List<OrderCartDto> mapToList(List<OrderCart> orderCartList);

    OrderCartDto map(OrderCart orderCart);

}
