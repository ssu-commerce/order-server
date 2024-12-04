package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.model.OrderCartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SelectCartItemParamDto {
    private UUID bookId;
    private UUID userId;
    private LocalDateTime addedAt;

    public SelectCartItemParamDto(UUID userId, OrderCartItem orderCartItem) {
        this.userId = userId;
        bookId = orderCartItem.getBookId();
        addedAt = orderCartItem.getAddedAt();
    }
}
