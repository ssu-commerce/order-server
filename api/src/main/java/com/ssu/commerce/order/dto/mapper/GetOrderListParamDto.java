package com.ssu.commerce.order.dto.mapper;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
@Builder
public class GetOrderListParamDto {
    private UUID userId;
    private Pageable pageable;
}
