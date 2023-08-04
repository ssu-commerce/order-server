package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ReturnBookRequestDto {

    private String orderItemId;
}
