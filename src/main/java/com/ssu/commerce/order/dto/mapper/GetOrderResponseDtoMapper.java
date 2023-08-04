package com.ssu.commerce.order.dto.mapper;

import com.ssu.commerce.order.dto.response.GetOrderResponseDto;
import com.ssu.commerce.order.dto.response.OrderWithItemsDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        typeConversionPolicy = ReportingPolicy.ERROR,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT
)
public interface GetOrderResponseDtoMapper {
    GetOrderResponseDtoMapper INSTANCE = Mappers.getMapper(GetOrderResponseDtoMapper.class);

    default Page<GetOrderResponseDto> mapToList(Page<OrderWithItemsDto> orderWithItemsDtoList) {
        return new PageImpl<>(orderWithItemsDtoList.stream()
                .map(this::map)
                .collect(Collectors.toList()));
    }

    GetOrderResponseDto map(OrderWithItemsDto dto);
}
