package com.ssu.commerce.order.dto.param.query;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SelectOrderCartParamDto {
    private UUID userId;
}
