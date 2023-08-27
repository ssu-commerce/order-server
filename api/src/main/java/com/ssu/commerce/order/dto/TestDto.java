package com.ssu.commerce.order.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TestDto {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Long price;
    private Long ownerId;
    private LocalDateTime publishDate;
    private String isbn;
    private Long maxBorrowDay;
    private Long categoryId;
}
