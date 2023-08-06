package com.ssu.commerce.order.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCartResponseDto {

    private String userId;

    private String bookId;

    private LocalDateTime addedAt;
}
