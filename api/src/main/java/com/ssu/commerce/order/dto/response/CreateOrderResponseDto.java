package com.ssu.commerce.order.dto.response;

import com.ssu.commerce.order.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class CreateOrderResponseDto {
    private UUID orderId;

    public CreateOrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
    }
}
