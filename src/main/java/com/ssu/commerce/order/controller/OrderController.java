package com.ssu.commerce.order.controller;

import com.ssu.commerce.core.security.AuthInfo;
import com.ssu.commerce.core.security.Authenticated;
import com.ssu.commerce.order.constants.OrderConstant;
import com.ssu.commerce.order.dto.mapper.*;
import com.ssu.commerce.order.dto.request.RegisterBookToCartRequestDto;
import com.ssu.commerce.order.dto.request.RentalBookListRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.*;
import com.ssu.commerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OrderConstant.ORDER_API_BASE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/book/rental")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto rentalBook(
            @RequestBody RentalBookListRequestDto requestDto,
            @Authenticated AuthInfo authInfo
    ) {

        log.debug("[rentalBook]RentalBookListRequestDto={}", requestDto);

        return OrderResponseDtoMapper.INSTANCE.map(
                orderService.rentalBook(
                        requestDto, authInfo.getUserId()
                ).getUserId()
        );
    }

    @PostMapping("/book/return")
    @ResponseStatus(HttpStatus.OK)
    public ReturnBookResponseDto returnBook(
            @RequestBody ReturnBookRequestDto requestDto
    ) {
        log.debug("[returnBook]ReturnBookRequestDto={}", requestDto);

        return ReturnBookResponseDtoMapper.INSTANCE.map(
                orderService.returnBook(requestDto)
                        .getId()
        );
    }

    @GetMapping("/book")
    public Page<GetOrderResponseDto> getOrderList(
            Pageable pageable,
            @Authenticated AuthInfo authInfo
    ) {
        log.debug("[getOrderList]authInfo={}", authInfo);

        return orderService.getOrderList(
                GetOrderListParamMapper.INSTANCE.map(
                        authInfo.getUserId(),
                        pageable
                )
        );
    }

    }
}