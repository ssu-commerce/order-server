package com.ssu.commerce.order.dto.response;

import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CartItemResponseDto {

    private String userId;

    private String bookId;

    private LocalDateTime addedAt;

    public CartItemResponseDto(SelectCartItemParamDto dto) {
        userId = dto.getUserId().toString();
        bookId = dto.getBookId().toString();
        addedAt = dto.getAddedAt();
    }
}
