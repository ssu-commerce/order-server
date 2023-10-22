package com.ssu.commerce.order.controller;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.dto.mapper.GetOrderCartListParamMapper;
import com.ssu.commerce.order.dto.mapper.OrderCartResponseDtoMapper;
import com.ssu.commerce.order.dto.mapper.RegisterBookToCartParamDtoMapper;
import com.ssu.commerce.order.dto.request.RegisterBookToCartRequestDto;
import com.ssu.commerce.order.dto.response.AddBookToCartResponseDto;
import com.ssu.commerce.order.dto.response.DeleteBookFromCartResponseDto;
import com.ssu.commerce.order.dto.response.OrderCartResponseDto;
import com.ssu.commerce.order.service.CartService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    @GetMapping
    public Page<OrderCartResponseDto> getBookListFromCart(
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal,
            Pageable pageable
    ) {
        log.debug("getBookListFromCart]SsuCommerceAuthenticatedPrincipal={}", principal);

        return cartService.getCartItemList(
                        GetOrderCartListParamMapper.INSTANCE.map(
                                principal.getUserId()
                                , pageable
                        ))
                .map(OrderCartResponseDtoMapper.INSTANCE::map);
    }

    @PostMapping
    public AddBookToCartResponseDto registerBookToCart(
            @Valid @RequestBody final RegisterBookToCartRequestDto requestDto,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal
    ) {
        log.debug("[addBookToCart]requestDto={}", requestDto);

        return AddBookToCartResponseDto.builder()
                .id(
                        cartService.addBookToCart(
                                RegisterBookToCartParamDtoMapper.INSTANCE.map(requestDto),
                                principal.getUserId()
                        )
                )
                .build();
    }

    @DeleteMapping("/{bookId}")
    public DeleteBookFromCartResponseDto deleteBookFromCart(
            @PathVariable final UUID bookId
    ) {
        log.debug("[deleteBookFromCart]bookId={}", bookId);

        return DeleteBookFromCartResponseDto.builder()
                .id(
                        cartService.deleteBookFromCart(bookId)
                )
                .build();
    }
}
