package com.ssu.commerce.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UpdateOrderItemResponseDto {
    private String orderItemId;
}
