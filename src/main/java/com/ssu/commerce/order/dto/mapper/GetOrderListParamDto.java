package com.ssu.commerce.order.dto.mapper;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class GetOrderListParamDto {
    private String userId;
    private Pageable pageable;
}
