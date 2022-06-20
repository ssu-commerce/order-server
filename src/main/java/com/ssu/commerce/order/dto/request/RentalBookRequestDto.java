package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RentalBookRequestDto {

    private Long bookId;
    private String orderLocation; // 도서 배송 위치
}
