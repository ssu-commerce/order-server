package com.ssu.commerce.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ReturnBookResponseDto {
    private String orderItemId;
}