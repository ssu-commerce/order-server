package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.model.OrderItem;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface OrderItemListMapper {
    OrderItemListMapper INSTANCE = Mappers.getMapper(OrderItemListMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderState", ignore = true)
    OrderItem map(RentalBookRequestDto requestDto, UUID orderId);

    @ObjectFactory
    List<OrderItem> mapToList(List<RentalBookRequestDto> requestDtoList, UUID orderId);

    @AfterMapping
    default void setDefaultValues(@MappingTarget OrderItem orderItem) {
        orderItem.setOrderState(OrderState.REGISTERED);
    }
}
