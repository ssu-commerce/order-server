package com.ssu.commerce.order.supplier;

import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public interface OrderServiceTestDataSupplier {

    UUID TEST_VAL_USER_ID = UUID.fromString("60180817-de61-4bac-a777-16219ffb92c0");
    UUID TEST_VAL_ORDER_ID = UUID.fromString("578caff7-1c44-4ede-92d2-b6e6cf60c0aa");
    UUID TEST_VAL_BOOK_ID = UUID.fromString("f29eb3c9-2e17-4e62-80d9-07573dec62f8");
    UUID TEST_VAL_ORDER_ITEM_ID = UUID.fromString("f45ff14c-69bd-4db7-af5d-8cfea9a30f85");
    LocalDateTime TEST_VAL_DATE_TIME = LocalDateTime.now();

    LocalDateTime TEST_VAL_DATE_END = LocalDateTime.now().plusDays(10L);
    static GetOrderListParamDto getOrderListParamDto() {
        return GetOrderListParamDto.builder()
                .userId(TEST_VAL_USER_ID)
                .pageable(Pageable.unpaged())
                .build();
    }

    static Page<OrderListParamDto> getOrderListParamDtoPage() {
        return new PageImpl<>(
                Arrays.asList(
                        OrderListParamDto.builder()
                                .id(TEST_VAL_ORDER_ID)
                                .orderedAt(TEST_VAL_DATE_TIME)
                                .userId(TEST_VAL_USER_ID)
                                .build()
                ));
    }

    static Page<Order> getOrderPage() {
        return new PageImpl<>(
                Arrays.asList(
                        Order.builder()
                                .id(TEST_VAL_ORDER_ID)
                                .userId(TEST_VAL_USER_ID)
                                .orderedAt(TEST_VAL_DATE_TIME)
                                .build()
                )
        );
    }

    static RentalBookRequestDto getRentalBookRequestDto() {
        return RentalBookRequestDto.builder()
                .bookId(TEST_VAL_BOOK_ID)
                .startedAt(TEST_VAL_DATE_TIME)
                .endAt(TEST_VAL_DATE_END)
                .build();
    }

    static Order getOrder() {
        return Order.builder()
                .orderedAt(TEST_VAL_DATE_TIME)
                .userId(TEST_VAL_USER_ID)
                .build();
    }

    static Order getOrderWithId() {
        return Order.builder()
                .id(TEST_VAL_ORDER_ID)
                .orderedAt(TEST_VAL_DATE_TIME)
                .userId(TEST_VAL_USER_ID)
                .build();
    }

    static OrderItem getOrderItem() {
        return OrderItem.builder()
                .id(TEST_VAL_ORDER_ITEM_ID)
                .bookId(TEST_VAL_BOOK_ID)
                .orderId(TEST_VAL_ORDER_ID)
                .orderState(OrderState.REGISTERED)
                .startedAt(TEST_VAL_DATE_TIME)
                .endAt(TEST_VAL_DATE_END)
                .build();
    }
}
