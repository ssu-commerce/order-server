package com.ssu.commerce.order.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddBookToCartResponseDto {
    private UUID id;
}
