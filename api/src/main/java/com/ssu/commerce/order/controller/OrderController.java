package com.ssu.commerce.order.controller;

import com.ssu.commerce.core.security.AuthInfo;
import com.ssu.commerce.core.security.Authenticated;
import com.ssu.commerce.order.GetBookResponseDto;
import com.ssu.commerce.order.GetPageDto;
import com.ssu.commerce.order.TestFeignClient;
import com.ssu.commerce.order.constants.OrderConstant;
import com.ssu.commerce.order.dto.mapper.*;
import com.ssu.commerce.order.dto.param.SelectOrderCartParamDto;
import com.ssu.commerce.order.dto.request.RegisterBookToCartRequestDto;
import com.ssu.commerce.order.dto.request.RentalBookListRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.*;
import com.ssu.commerce.order.service.OrderService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(OrderConstant.ORDER_API_BASE)
public class OrderController {

    @Autowired
    private final OrderService orderService;

    @Autowired
    private final TestFeignClient testFeignClient;

    @PostMapping("/book/rental")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto rentalBook(
            @NotNull @RequestBody final RentalBookListRequestDto requestDto,
            @NotNull @Authenticated @Parameter(hidden = true) final AuthInfo authInfo
    ) {

        log.debug("[rentalBook]RentalBookListRequestDto={}", requestDto);

        return OrderResponseDtoMapper.INSTANCE.map(
                orderService.rentalBook(
                        requestDto,
                        UUID.fromString(authInfo.getUserId())
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
            @NotNull @Authenticated @Parameter(hidden = true) final AuthInfo authInfo,
            Pageable pageable
    ) {
        log.debug("getBookListFromCart]authInfo={}", authInfo);

        final Page<SelectOrderCartParamDto> selectOrderCartParamDtoPage = orderService.getCartItemList(
                GetOrderCartListParamMapper.INSTANCE.map(authInfo.getUserId(), pageable)
        );

        return new PageImpl<>(
                OrderCartResponseDtoMapper.INSTANCE.mapToList(selectOrderCartParamDtoPage.getContent()),
                selectOrderCartParamDtoPage.getPageable(),
                selectOrderCartParamDtoPage.getTotalElements()

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
                                UUID.fromString(authInfo.getUserId())
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

    @GetMapping("/test")
    public List<GetBookResponseDto> test() {
        LocalDateTime startTime = LocalDateTime.now();
        GetPageDto dtoList =  testFeignClient.getTest(0, "title", 1L);

        LocalDateTime endTime = LocalDateTime.now();

        Duration duration = Duration.between(startTime, endTime);
        log.info("[feignClientTime] : {}", duration.toMillis());
        return dtoList.getContents();
    }
}
