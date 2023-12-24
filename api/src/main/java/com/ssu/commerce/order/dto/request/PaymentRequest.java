package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
public class PaymentRequest {
    private UUID senderId;
    private UUID receiverId;
    private Long amount;
}
