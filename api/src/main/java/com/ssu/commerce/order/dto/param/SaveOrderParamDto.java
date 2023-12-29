package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@EqualsAndHashCode
public class SaveOrderParamDto {
    private UUID userId;
    private String accessToken;
    private List<CreateOrderInfoDto> requestDto;
    private Long paymentId;
}
