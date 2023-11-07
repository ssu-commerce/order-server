package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.dto.mapper.SelectOrderCartListParamDtoMapper;
import com.ssu.commerce.order.dto.mapper.SelectOrderCartParamDtoMapper;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public UUID createCartItem(
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

        return cartItemRepository.save(
                OrderCartItem.builder()
                        .bookId(paramDto.getBookId())
                        .orderCartId(orderCart.getId())
                        .addedAt(LocalDateTime.now())
                        .build()
        ).getId();
    }

    @Transactional
    public UUID deleteCartItem(UUID id) {
        OrderCartItem orderCartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("cartItem not found; cartItemId=%s", id),
                        "ORDER_CART_001"
                ));
        cartItemRepository.delete(orderCartItem);
        return orderCartItem.getId();
    }

    public Page<SelectCartItemParamDto> getCartItem(
            @NonNull final CartItemParamDto paramDto
    ) {

        return cartRepository.selectOrderCartPage(
                SelectOrderCartListParamDtoMapper.INSTANCE.map(paramDto),
                paramDto.getPageable()
        ).map(SelectOrderCartParamDtoMapper.INSTANCE::map);
    }
}
