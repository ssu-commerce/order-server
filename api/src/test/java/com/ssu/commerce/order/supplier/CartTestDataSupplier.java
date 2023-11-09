package com.ssu.commerce.order.supplier;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.core.security.user.UserRole;
import com.ssu.commerce.order.dto.param.CartItemParamDto;
import com.ssu.commerce.order.dto.param.CreateCartItemParamDto;
import com.ssu.commerce.order.dto.param.SelectCartItemParamDto;
import com.ssu.commerce.order.dto.param.SelectOrderCartDto;
import com.ssu.commerce.order.dto.request.CreateCartItemRequestDto;
import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public interface CartTestDataSupplier {

    UUID TEST_VAL_BOOK_ID = UUID.fromString("48a46ad5-2ecd-4a61-81ad-b25a6df896c5");
    UUID TEST_VAL_USER_ID = UUID.fromString("b94c78ae-542c-4f04-b392-4dd107252645");
    UUID TEST_VAL_CART_ITEM_ID = UUID.fromString("b8580a51-c043-4ea8-aff1-8f9fc595921a");
    UUID TEST_VAL_ORDER_CART_ID = UUID.fromString("1f877ef4-ac3c-402e-a46c-6f8d306fadc2");
    String TEST_VAL_ACCESS_TOKEN = "TEST_TOKEN";

    static SsuCommerceAuthenticatedPrincipal getSsuCommerceAuthenticatedPrincipal() {
        return new SsuCommerceAuthenticatedPrincipal(
                TEST_VAL_USER_ID,
                TEST_VAL_ACCESS_TOKEN,
                Arrays.asList(
                        UserRole.ROLE_USER
                ),
                "USER_NAME"
        );
    }

    static CreateCartItemRequestDto getCreateCartItemRequestDto() {
        return CreateCartItemRequestDto.builder()
                .bookId(TEST_VAL_BOOK_ID)
                .build();
    }

    static Page<SelectCartItemParamDto> getSelectCartItemParamDto() {
        return new PageImpl<>(
                Arrays.asList(
                        SelectCartItemParamDto.builder()
                                .bookId(TEST_VAL_BOOK_ID)
                                .addedAt(LocalDateTime.now())
                                .build()
                )
        );
    }

    static CreateCartItemParamDto getCreateCartItemDto() {
        return CreateCartItemParamDto.builder()
                .bookId(TEST_VAL_BOOK_ID)
                .build();
    }

    static OrderCart getOrderCart() {
        return OrderCart.builder()
                .id(TEST_VAL_ORDER_CART_ID)
                .userId(TEST_VAL_USER_ID)
                .build();
    }

    static OrderCartItem getOrderCartItem() {
        return OrderCartItem.builder()
                .id(TEST_VAL_CART_ITEM_ID)
                .orderCartId(TEST_VAL_ORDER_CART_ID)
                .addedAt(LocalDateTime.now())
                .bookId(TEST_VAL_BOOK_ID)
                .build();
    }

    static CartItemParamDto getCartItemParamDto() {
        return CartItemParamDto.builder()
                .userId(TEST_VAL_USER_ID)
                .pageable(Pageable.unpaged())
                .build();
    }
    static Page<SelectOrderCartDto> getSelectOrderCartDto() {
        return new PageImpl<>(
                Arrays.asList(
                        SelectOrderCartDto.builder()
                                .orderCart(
                                        OrderCart.builder()
                                                .id(TEST_VAL_ORDER_CART_ID)
                                                .userId(TEST_VAL_USER_ID)
                                                .build()
                                )
                                .orderCartItemList(
                                        OrderCartItem.builder()
                                                .orderCartId(TEST_VAL_ORDER_CART_ID)
                                                .bookId(TEST_VAL_BOOK_ID)
                                                .addedAt(LocalDateTime.now())
                                                .build()
                                )
                                .build()
                )
        );
    }
}
