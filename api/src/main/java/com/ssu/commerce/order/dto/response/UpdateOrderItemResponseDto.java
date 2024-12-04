package com.ssu.commerce.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UpdateOrderItemResponseDto {
    private String orderItemId;

    public UpdateOrderItemResponseDto(UUID orderItemId) {
        this.orderItemId = orderItemId.toString();
    }
}
