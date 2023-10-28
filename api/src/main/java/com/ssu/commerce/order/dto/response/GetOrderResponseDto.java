package com.ssu.commerce.order.dto.response;

import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import lombok.Getter;

import java.util.List;

@Getter
public class GetOrderResponseDto {
    private Order order;
    private List<OrderItem> orderItems;
}
