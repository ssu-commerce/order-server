package com.ssu.commerce.order.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderCartDto {
    private String userId;

    private String bookId;

    private LocalDateTime addedAt;
}
