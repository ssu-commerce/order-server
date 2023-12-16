package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CreateOrderInfoDto {
    private UUID bookId;
    private LocalDateTime startedAt;
    private LocalDateTime endAt;
    private Long price;
}
