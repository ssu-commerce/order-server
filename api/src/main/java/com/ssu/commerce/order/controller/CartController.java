package com.ssu.commerce.order.controller;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.dto.param.CartItemParamDto;
import com.ssu.commerce.order.dto.param.CreateCartItemParamDto;
import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
import com.ssu.commerce.order.dto.response.CreateCartItemResponseDto;
import com.ssu.commerce.order.dto.response.DeleteCartItemResponseDto;
import com.ssu.commerce.order.dto.response.CartItemResponseDto;
import com.ssu.commerce.order.service.CartService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    public Page<CartItemResponseDto> getCartItem(
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal,
            Pageable pageable
    ) {
        log.debug("getCartItem]SsuCommerceAuthenticatedPrincipal={}", principal);

        return cartService.getCartItem(new CartItemParamDto(principal.getUserId(), pageable))
                .map(CartItemResponseDto::new);
    }

    @PostMapping
    public CreateCartItemResponseDto createCartItem(
            @Valid @RequestBody final CreateCartItemRequestDto requestDto,
            @NotNull @AuthenticationPrincipal @Parameter(hidden = true)  final SsuCommerceAuthenticatedPrincipal principal
    ) {
        log.debug("[createCartItem]requestDto={}", requestDto);

        return CreateCartItemResponseDto.builder()
                .cartItemIds(
                        cartService.createCartItem(
                                new CreateCartItemParamDto(requestDto),
                                principal.getUserId()
                        )
                )
                .build();
    }

    @DeleteMapping("/{cartId}")
    public DeleteCartItemResponseDto deleteCartItem(
            @PathVariable final UUID cartId
    ) {
        log.debug("[deleteCartItem]cartId={}", cartId);

        return DeleteCartItemResponseDto.builder()
                .id(
                        cartService.deleteCartItem(cartId)
                )
                .build();
    }
}
