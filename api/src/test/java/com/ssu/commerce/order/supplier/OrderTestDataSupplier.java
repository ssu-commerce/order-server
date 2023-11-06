package com.ssu.commerce.order.supplier;

import com.google.type.DateTime;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.core.security.user.UserRole;
import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface OrderTestDataSupplier {

    UUID TEST_VAL_BOOK_ID = UUID.fromString("48a46ad5-2ecd-4a61-81ad-b25a6df896c5");
    UUID TEST_VAL_ANOTHER_BOOK_ID = UUID.fromString("8e4eed48-4f55-431b-99e2-64fd777fcf8b");
    UUID TEST_VAL_USER_ID = UUID.fromString("b94c78ae-542c-4f04-b392-4dd107252645");
    UUID TEST_VAL_ORDER_ID = UUID.fromString("3a88bd50-e2e5-4e17-9cb1-40324e93099b");
    UUID TEST_VAL_ORDER_ITEM_ID = UUID.fromString("508dc153-b376-4693-acf1-aed1e61bb593");

    String TEST_VAL_ACCESS_TOKEN = "TEST_TOKEN";
    LocalDateTime TEST_VAL_ORDERED_AT = LocalDateTime.now();
    LocalDateTime TEST_VAL_STARTED_AT = LocalDateTime.now().plusDays(1);
    LocalDateTime TEST_VAL_END_AT = LocalDateTime.now().plusMonths(1);

    static List<CreateOrderRequestDto> getCreateOrderRequestDto() {
        return Arrays.asList(
                CreateOrderRequestDto.builder()
                        .bookId(TEST_VAL_BOOK_ID)
                        .startedAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().plusDays(10))
                        .build(),
                CreateOrderRequestDto.builder()
                        .bookId(TEST_VAL_ANOTHER_BOOK_ID)
                        .startedAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().plusMonths(1))
                        .build()
        );
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
                TEST_VAL_ACCESS_TOKEN,
                Arrays.asList(
                        UserRole.ROLE_USER
                ),
                "USER_NAME"
        );
    }

    static GetOrderListParamDto getGetOrderListParamDto() {
        return GetOrderListParamDto.builder()
                .userId(TEST_VAL_USER_ID)
                .pageable(Pageable.unpaged())
                .build();
    }

    static OrderListParamDto getOrderListParamDto() {
        return OrderListParamDto.builder()
                .id(TEST_VAL_ORDER_ID)
                .userId(TEST_VAL_USER_ID)
                .orderedAt(LocalDateTime.now())
                .build();
    }


    static Page<OrderListParamDto> getOrderListParamDtoPage() {
        return new PageImpl<>(
                Arrays.asList(
                        OrderListParamDto.builder()
                                .id(TEST_VAL_ORDER_ID)
                                .orderedAt(TEST_VAL_ORDERED_AT)
                                .userId(TEST_VAL_USER_ID)
                                .build()
                )
        );
    }

    static Page<Order> getOrderPage() {
        return new PageImpl<>(
                Arrays.asList(
                        Order.builder()
                                .id(TEST_VAL_ORDER_ID)
                                .userId(TEST_VAL_USER_ID)
                                .orderedAt(TEST_VAL_ORDERED_AT)
                                .build()
                )
        );
    }

    static OrderItem getOrderItem() {
        return OrderItem.builder()
                .id(TEST_VAL_ORDER_ITEM_ID)
                .orderState(OrderState.REGISTERED)
                .bookId(TEST_VAL_BOOK_ID)
                .orderId(TEST_VAL_ORDER_ID)
                .startedAt(TEST_VAL_STARTED_AT)
                .endAt(TEST_VAL_END_AT)
                .build();
    }
}
