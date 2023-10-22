package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.dto.mapper.SelectOrderCartListParamDtoMapper;
import com.ssu.commerce.order.dto.mapper.SelectOrderCartParamDtoMapper;
import com.ssu.commerce.order.dto.param.GetOrderCartListParamDto;
import com.ssu.commerce.order.dto.param.RegisterBookToCartParamDto;
import com.ssu.commerce.order.dto.param.SelectOrderCartParamDto;
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
    public UUID addBookToCart(
            @NonNull @Valid RegisterBookToCartParamDto paramDto,
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
    public UUID deleteBookFromCart(UUID id) {
        OrderCart orderCart = cartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("orderCart not found; cartId=%s", id),
                        "ORDER_CART_001"
                ));
        cartRepository.delete(orderCart);
        return orderCart.getId();
    }

    public Page<SelectOrderCartParamDto> getCartItemList(
            @NonNull final GetOrderCartListParamDto paramDto
    ) {

        return cartRepository.selectOrderCartPage(
                SelectOrderCartListParamDtoMapper.INSTANCE.map(paramDto),
                paramDto.getPageable()
        ).map(SelectOrderCartParamDtoMapper.INSTANCE::map);
    }
}
