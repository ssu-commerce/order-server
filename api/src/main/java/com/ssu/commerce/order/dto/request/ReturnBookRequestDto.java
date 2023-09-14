package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ReturnBookRequestDto {

    private UUID orderItemId;
}
