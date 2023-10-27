package com.ssu.commerce.order.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CartItemResponseDto {

    private String userId;

    private String bookId;

    private LocalDateTime addedAt;
}
