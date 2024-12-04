package com.ssu.commerce.order.dto.param;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Getter
@Builder
public class CartItemParamDto {
    private UUID userId;
    private Pageable pageable;

    public CartItemParamDto(UUID userId, Pageable pageable) {
        this.userId = userId;
        this.pageable = pageable;
    }
}
