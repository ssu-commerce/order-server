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
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
            @NotNull @RequestBody final RentalBookListRequestDto requestDto,
            @NotNull @Authenticated @Parameter(hidden = true) final AuthInfo authInfo
    ) {

        log.debug("[rentalBook]RentalBookListRequestDto={}", requestDto);

        return OrderResponseDtoMapper.INSTANCE.map(
                orderService.rentalBook(
                        requestDto, authInfo.getUserId()
                ).getId()
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
                        .getId()
        );
    }

    @GetMapping("/book")
    public Page<GetOrderResponseDto> getOrderList(
            Pageable pageable,
            @NotNull @Authenticated @Parameter(hidden = true) final AuthInfo authInfo
    ) {
        log.debug("[getOrderList]authInfo={}", authInfo);

        return orderService.getOrderList(
                GetOrderListParamMapper.INSTANCE.map(
                        authInfo.getUserId(),
                        pageable
                )
        );
    }

    @PostMapping("/approve/{id}")
    public ApproveRentalResponseDto approveRental (
            @PathVariable final String id
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
            @PathVariable final String id
    ) {
        log.debug("[rejectRental]id={}", id);

        return RejectRentalResponseDto.builder()
                .id(
                        orderService.rejectRental(id)
                )
                .build();
    }


    @GetMapping("/cart")
    public List<OrderCartResponseDto> getBookListFromCart(
            @NotNull @Authenticated @Parameter(hidden = true) final AuthInfo authInfo,
            Pageable pageable
    ) {
        log.debug("getBookListFromCart]authInfo={}", authInfo);

        return OrderCartResponseDtoMapper.INSTANCE.mapToList(
                orderService.getCartItemList(
                        GetOrderCartListParamMapper.INSTANCE.map(authInfo.getUserId(), pageable)
                ).getContent()
        );
    }

    @PostMapping("/cart")
    public AddBookToCartResponseDto registerBookToCart(
            @Valid @RequestBody final RegisterBookToCartRequestDto requestDto,
            @Authenticated @Parameter(hidden = true) AuthInfo authInfo
    ) {
        log.debug("[addBookToCart]requestDto={}", requestDto);

        return AddBookToCartResponseDto.builder()
                .id(
                        orderService.addBookToCart(
                                RegisterBookToCartParamDtoMapper.INSTANCE.map(requestDto),
                                authInfo.getUserId()
                        )
                )
                .build();
    }

    @DeleteMapping("/cart/{bookId}")
    public DeleteBookFromCartResponseDto deleteBookFromCart(
            @PathVariable final String bookId
    ) {
        log.debug("[deleteBookFromCart]bookId={}", bookId);

        return DeleteBookFromCartResponseDto.builder()
                .id(
                        orderService.deleteBookFromCart(bookId)
                )
                .build();
    }
}
