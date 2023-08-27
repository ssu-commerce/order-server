package com.ssu.commerce.order.dto.param.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelectOrderListParamDto {
    private String userId;
}
