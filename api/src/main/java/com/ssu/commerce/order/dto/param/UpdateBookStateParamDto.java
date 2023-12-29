package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
import com.ssu.commerce.order.grpc.BookState;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@EqualsAndHashCode
public class UpdateBookStateParamDto {
    private List<CreateOrderInfoDto> createOrderInfoDto;
    private String accessToken;
    private BookState bookState;
}
