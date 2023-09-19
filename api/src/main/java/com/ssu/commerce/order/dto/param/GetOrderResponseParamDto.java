package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class GetOrderResponseParamDto {
    private Order order;
    private List<OrderItem> orderItems;
}
