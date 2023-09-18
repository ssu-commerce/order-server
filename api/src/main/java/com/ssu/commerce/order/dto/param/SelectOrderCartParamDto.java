package com.ssu.commerce.order.dto.param;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class SelectOrderCartParamDto {
    private UUID userId;
    private UUID bookId;
    private LocalDateTime addedAt;
}
