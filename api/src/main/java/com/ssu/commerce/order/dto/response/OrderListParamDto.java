package com.ssu.commerce.order.dto.response;


import com.ssu.commerce.order.model.Order;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderListParamDto {

    private UUID id;

    private LocalDateTime orderedAt;

    private UUID userId;

    public OrderListParamDto(Order order) {
        id = order.getOrderId();
        orderedAt = order.getOrderedAt();
        userId = order.getUserId();
    }
}
