package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.dto.param.CartItemParamDto;
import com.ssu.commerce.order.dto.param.CreateCartItemParamDto;
import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import com.ssu.commerce.order.persistence.CartItemRepository;
import com.ssu.commerce.order.persistence.CartRepository;
import com.ssu.commerce.order.supplier.CartTestDataSupplier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest implements CartTestDataSupplier {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @Test
    void createCartItem_success_exists_cart() {
        CreateCartItemParamDto paramDto = CartTestDataSupplier.getCreateCartItemDto();
        OrderCart orderCart = CartTestDataSupplier.getOrderCart();

        when(cartRepository.findByUserId(TEST_VAL_USER_ID)).thenReturn(Optional.ofNullable(orderCart));
        when(cartItemRepository.save(
                argThat(cartItem -> cartItem != null &&
                        cartItem.getBookId().equals(TEST_VAL_BOOK_ID) && cartItem.getOrderCartId().equals(TEST_VAL_ORDER_CART_ID)
        ))).thenReturn(CartTestDataSupplier.getOrderCartItem());

        UUID cartId = cartService.createCartItem(paramDto, TEST_VAL_USER_ID);

        assertEquals(TEST_VAL_CART_ITEM_ID, cartId);

        verify(cartRepository).findByUserId(TEST_VAL_USER_ID);
        verify(cartItemRepository).save(argThat(cartItem -> cartItem != null &&
                cartItem.getBookId().equals(TEST_VAL_BOOK_ID) && cartItem.getOrderCartId().equals(TEST_VAL_ORDER_CART_ID)
        ));
    }

    @Test
    void createCartItem_success_not_exists_cart() {
        CreateCartItemParamDto paramDto = CartTestDataSupplier.getCreateCartItemDto();
        OrderCart orderCart = CartTestDataSupplier.getOrderCart();

        when(cartRepository.findByUserId(TEST_VAL_USER_ID)).thenReturn(Optional.empty());
        when(cartRepository.save(
                argThat(cart -> cart != null && cart.getUserId().equals(TEST_VAL_USER_ID)
        ))).thenReturn(orderCart);
        when(cartItemRepository.save(
                argThat(cartItem -> cartItem != null && cartItem.getBookId().equals(TEST_VAL_BOOK_ID)
                        && cartItem.getOrderCartId().equals(TEST_VAL_ORDER_CART_ID)
                ))).thenReturn(CartTestDataSupplier.getOrderCartItem());

        UUID cartId = cartService.createCartItem(paramDto, TEST_VAL_USER_ID);

        assertEquals(TEST_VAL_CART_ITEM_ID, cartId);

        verify(cartRepository).findByUserId(TEST_VAL_USER_ID);
        verify(cartRepository).save(argThat(cart -> cart != null && cart.getUserId().equals(TEST_VAL_USER_ID)));
        verify(cartItemRepository).save(argThat(cartItem -> cartItem != null &&
                cartItem.getBookId().equals(TEST_VAL_BOOK_ID) && cartItem.getOrderCartId().equals(TEST_VAL_ORDER_CART_ID)
        ));
    }

    @Test
    void deleteCartItem_success() {
        OrderCartItem orderCartItem = CartTestDataSupplier.getOrderCartItem();

        when(cartItemRepository.findById(TEST_VAL_CART_ITEM_ID)).thenReturn(Optional.ofNullable(orderCartItem));
        UUID returnId = cartService.deleteCartItem(TEST_VAL_CART_ITEM_ID);

        assertEquals(TEST_VAL_CART_ITEM_ID, returnId);
        verify(cartItemRepository).findById(TEST_VAL_CART_ITEM_ID);
    }

    @Test
    void deleteCartItem_fail_NotFoundException() {
        NotFoundException exception = new NotFoundException(
                String.format("cartItem not found; cartItemId=%s", TEST_VAL_CART_ITEM_ID),
                "ORDER_CART_001");

        when(cartItemRepository.findById(TEST_VAL_CART_ITEM_ID)).thenReturn(Optional.empty());

        NotFoundException notFoundException = assertThrows(NotFoundException.class, () -> {
            cartService.deleteCartItem(TEST_VAL_CART_ITEM_ID);
        });

        assertEquals(exception.getMessage(), notFoundException.getMessage());
        verify(cartItemRepository).findById(TEST_VAL_CART_ITEM_ID);
    }

    @Test
    void getCartItem_success() {
        CartItemParamDto paramDto = CartTestDataSupplier.getCartItemParamDto();

        when(cartRepository.selectOrderCartPage(
                argThat(dto1 ->
                    dto1 != null && dto1.getUserId().equals(TEST_VAL_USER_ID)
                ), eq(Pageable.unpaged())
        )).thenReturn(CartTestDataSupplier.getSelectOrderCartDto());

        Page<SelectCartItemParamDto> dto = cartService.getCartItem(paramDto);

        assertEquals(dto.getContent().get(0).getBookId(), TEST_VAL_BOOK_ID);
        assertEquals(dto.getContent().size(), 1);
        verify(cartRepository).selectOrderCartPage(
                argThat(dto2 ->
                        dto2 != null && dto2.getUserId().equals(TEST_VAL_USER_ID)
                ), eq(Pageable.unpaged())
        );
    }
}
