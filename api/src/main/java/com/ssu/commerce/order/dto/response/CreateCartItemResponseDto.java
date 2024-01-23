package com.ssu.commerce.order.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
public class CreateCartItemResponseDto {
    private List<UUID> cartItemIds;
}
