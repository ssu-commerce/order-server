package com.ssu.commerce.order.dto.param.query;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
@Builder
public class SelectOrderListParamDto {
    private UUID userId;
    private Pageable pageable;
}
