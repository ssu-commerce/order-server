package com.ssu.commerce.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class CreateCartItemResponseDto {
    private UUID id;
}
