package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RentalBookRequestDto {
    private String bookId;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
}
