package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class RentalBookRequestDto {
    private UUID bookId;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
}
