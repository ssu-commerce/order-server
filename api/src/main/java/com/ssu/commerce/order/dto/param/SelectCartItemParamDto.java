package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class SelectCartItemParamDto {
    private UUID bookId;
    private LocalDateTime addedAt;
}
