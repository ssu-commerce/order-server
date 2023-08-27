package com.ssu.commerce.order.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteBookFromCartResponseDto {
    private String id;
}
