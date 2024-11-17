package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.dto.param.CartItemParamDto;
import com.ssu.commerce.order.dto.param.CreateCartItemParamDto;
import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import com.ssu.commerce.order.persistence.CartItemRepository;
import com.ssu.commerce.order.persistence.CartRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public List<UUID> createCartItem(
            @NonNull @Valid CreateCartItemParamDto paramDto,
            UUID userId
    ) {

        OrderCart orderCart = cartRepository.findByUserId(userId)
                .orElseGet(() ->
                        cartRepository.save(
                                OrderCart.builder()
                                        .userId(userId)
                                        .build()
                        )
                );

        return cartItemRepository.saveAll(
                paramDto.getBookIds().stream().map(id ->
                        OrderCartItem.builder()
                                .bookId(id)
                                .orderCartId(orderCart.getOrderCartId())
                                .addedAt(LocalDateTime.now())
                                .build()
                ).collect(Collectors.toList())
        ).stream().map(OrderCartItem::getOrderCartId).collect(Collectors.toList());
    }

    @Transactional
    public UUID deleteCartItem(UUID id) {
        OrderCartItem orderCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("cartItem not found; cartItemId=%s", id),
                        "ORDER_CART_001"
                ));
        cartItemRepository.delete(orderCartItem);
        return orderCartItem.getOrderCartItemId();
    }

    public Page<SelectCartItemParamDto> getCartItem(
            @NonNull final CartItemParamDto paramDto
    ) {

        OrderCart orderCart = cartRepository.findByUserId(paramDto.getUserId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("cart not found; userId=%s", paramDto.getUserId()),
                        "ORDER_CART_002"
                ));

        Page<OrderCartItem> orderCartItems = cartItemRepository.findByOrderCartId(orderCart.getOrderCartId(), paramDto.getPageable());

        return orderCartItems.map(SelectCartItemParamDto::new);
    }
}
