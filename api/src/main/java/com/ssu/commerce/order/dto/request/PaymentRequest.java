package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class PaymentRequest {
    private UUID senderId;
    private UUID receiverId;
    private Long amount;
}
