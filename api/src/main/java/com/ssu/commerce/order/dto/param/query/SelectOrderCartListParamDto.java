package com.ssu.commerce.order.dto.param.query;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SelectOrderCartListParamDto {
    private UUID userId;
}
