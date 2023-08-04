package com.ssu.commerce.order.dto.response;

import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import lombok.Data;

import java.util.List;

public class OrderWithItemsDto {
    private Order order;
    private List<OrderItem> orderItems;

}
