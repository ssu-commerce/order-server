package com.ssu.commerce.order.supplier;

import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.core.security.user.UserRole;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.model.Order;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public interface OrderControllerTestDataSupplier {

    UUID TEST_VAL_BOOK_ID = UUID.fromString("48a46ad5-2ecd-4a61-81ad-b25a6df896c5");
    UUID TEST_VAL_ANOTHER_BOOK_ID = UUID.fromString("8e4eed48-4f55-431b-99e2-64fd777fcf8b");
    UUID TEST_VAL_USER_ID = UUID.fromString("b94c78ae-542c-4f04-b392-4dd107252645");
    UUID TEST_VAL_ORDER_ID = UUID.fromString("3a88bd50-e2e5-4e17-9cb1-40324e93099b");

    static CreateOrderRequestDto getRentalBookRequestDto() {
        return CreateOrderRequestDto.builder()
                .bookId(TEST_VAL_BOOK_ID)
                .startedAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().plusDays(10))
                .build();
    }

    static Order getOrder() {
        return Order.builder()
                .id(TEST_VAL_ORDER_ID)
                .orderedAt(LocalDateTime.now())
                .userId(TEST_VAL_USER_ID)
                .build();
    }

    static SsuCommerceAuthenticatedPrincipal getSsuCommerceAuthenticatedPrincipal() {
        return new SsuCommerceAuthenticatedPrincipal(
                TEST_VAL_USER_ID,
                "TEST_TOKEN",
                Arrays.asList(
                        UserRole.ROLE_USER
                ),
                "USER_NAME"
        );
    }
}
