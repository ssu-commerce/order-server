package com.ssu.commerce.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderResponseDto {

    private UUID id;
}
