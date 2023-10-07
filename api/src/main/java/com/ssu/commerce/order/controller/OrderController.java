package com.ssu.commerce.order.controller;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.constant.OrderConstant;
import com.ssu.commerce.order.dto.mapper.*;
import com.ssu.commerce.order.dto.request.RegisterBookToCartRequestDto;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.*;
import com.ssu.commerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OrderConstant.ORDER_API_BASE)
public class OrderController {

    @Autowired
    private final OrderService orderService;


    @PostMapping("/book/rental")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto rentalBook(
            @NotNull @RequestBody final RentalBookRequestDto requestDto,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true) final SsuCommerceAuthenticatedPrincipal principal
    ) {

        log.debug("[rentalBook]RentalBookRequestDto={}", requestDto);

        return OrderResponseDtoMapper.INSTANCE.map(
                orderService.rentalBook(
                        requestDto,
                        principal.getUserId()
                )
        );
    }

    @PostMapping("/book/return")
    @ResponseStatus(HttpStatus.OK)
    public ReturnBookResponseDto returnBook(
            @NotNull @RequestBody final ReturnBookRequestDto requestDto
    ) {
        log.debug("[returnBook]ReturnBookRequestDto={}", requestDto);

        return ReturnBookResponseDtoMapper.INSTANCE.map(
                orderService.returnBook(requestDto)
        );
    }

    @GetMapping("/book")
    public Page<GetOrderResponseDto> getOrderList(
            Pageable pageable,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal
    ) {
        log.debug("[getOrderList]SsuCommerceAuthenticatedPrincipal={}", principal);

        return orderService.getOrderList(
                GetOrderListParamDto.builder()
                        .userId(principal.getUserId().toString())
                        .pageable(pageable)
                        .build()
        ).map(GetOrderResponseDtoMapper.INSTANCE::map);
    }

    @PostMapping("/approve/{id}")
    public ApproveRentalResponseDto approveRental (
            @PathVariable final UUID id
    ) {
        log.debug("[approveRental]id={}", id);

        return ApproveRentalResponseDto.builder()
                .id(
                        orderService.approveRental(id)
                )
                .build();
    }

    @PostMapping("/reject/{id}")
    public RejectRentalResponseDto rejectRental (
            @PathVariable final UUID id
    ) {
        log.debug("[rejectRental]id={}", id);

        return RejectRentalResponseDto.builder()
                .id(
                        orderService.rejectRental(id)
                )
                .build();
    }


    @GetMapping("/cart")
    public Page<OrderCartResponseDto> getBookListFromCart(
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal,
            Pageable pageable
    ) {
        log.debug("getBookListFromCart]SsuCommerceAuthenticatedPrincipal={}", principal);

        return orderService.getCartItemList(
                        GetOrderCartListParamMapper.INSTANCE.map(
                                principal.getUserId().toString()
                                , pageable
                        ))
                .map(OrderCartResponseDtoMapper.INSTANCE::map);
    }

    @PostMapping("/cart")
    public AddBookToCartResponseDto registerBookToCart(
            @Valid @RequestBody final RegisterBookToCartRequestDto requestDto,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal
    ) {
        log.debug("[addBookToCart]requestDto={}", requestDto);

        return AddBookToCartResponseDto.builder()
                .id(
                        orderService.addBookToCart(
                                RegisterBookToCartParamDtoMapper.INSTANCE.map(requestDto),
                                principal.getUserId()
                        )
                )
                .build();
    }

    @DeleteMapping("/cart/{bookId}")
    public DeleteBookFromCartResponseDto deleteBookFromCart(
            @PathVariable final UUID bookId
    ) {
        log.debug("[deleteBookFromCart]bookId={}", bookId);

        return DeleteBookFromCartResponseDto.builder()
                .id(
                        orderService.deleteBookFromCart(bookId)
                )
                .build();
    }
}
