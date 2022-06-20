package com.ssu.commerce.order.dto.request;

import com.ssu.commerce.order.enums.DeliveryCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateOrderRequestDto {
    private Long bookId;
    private DeliveryCode deliveryCode;
}
