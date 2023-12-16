package com.ssu.commerce.order.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.dto.mapper.*;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.response.*;
import com.ssu.commerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CreateOrderResponseDto createOrder(
            @NotNull @RequestBody final CreateOrderRequestDto requestDto,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true) final SsuCommerceAuthenticatedPrincipal principal
    ) {

        log.debug("[createOrder]requestDto={}", requestDto);

        return OrderResponseDtoMapper.INSTANCE.map(
                orderService.createOrder(
                        requestDto.getOrderInfo(),
                        principal.getAccessToken(),
                        principal.getUserId(),
                        requestDto.getReceiverId()
                )
        );
    }

    @PutMapping("/{orderItemId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateOrderItemResponseDto updateOrderItem(
            @PathVariable UUID orderItemId
    ) {
        log.debug("[updateOrderItem]orderItemId={}", orderItemId);

        return UpdateOrderItemResponseDtoMapper.INSTANCE.map(
                orderService.updateOrderItem(orderItemId)
        );
    }

    @GetMapping
    public Page<OrderResponseDto> getOrder(
            Pageable pageable,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true) final SsuCommerceAuthenticatedPrincipal principal
    ) {
        log.debug("[getOrder]SsuCommerceAuthenticatedPrincipal={}", principal);

        return orderService.getOrderList(
                GetOrderListParamDto.builder()
                        .userId(principal.getUserId())
                        .pageable(pageable)
                        .build()
        ).map(orderListParamDto -> new OrderResponseDto(orderListParamDto));
    }
}
