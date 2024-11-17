package com.ssu.commerce.order.dto.param;

import com.ssu.commerce.grpc.BookState;
import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
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
