package com.ssu.commerce.order.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class RentalBookListRequestDto {
    private List<RentalBookRequestDto> rentalBookRequestDtoList;
}
