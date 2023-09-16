package com.ssu.commerce.order.supplier;

import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderTestDataSupplier {

    static Order getOrder() {
        return Order.builder()
                .id(UUID.randomUUID())
                .userId(UUID.randomUUID())
                .orderedAt(LocalDateTime.now().minusHours(10L))
                .build();
    }

    static OrderItem getOrderItem() {
        return OrderItem.builder()
                .id(UUID.randomUUID())
                .build();
    }

//    static OrderItem getOrderItem(String orderId) {

  //  }
}
