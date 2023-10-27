package com.ssu.commerce.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;

    private LocalDateTime orderedAt;

    private UUID userId;
    public OrderResponseDto(OrderListParamDto orderListParamDto) {
        id = orderListParamDto.getId();
        orderedAt = orderListParamDto.getOrderedAt();
        userId = orderListParamDto.getUserId();
    }
}
