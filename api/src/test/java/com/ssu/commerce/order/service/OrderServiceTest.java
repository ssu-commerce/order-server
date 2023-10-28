package com.ssu.commerce.order.service;

import com.ssu.commerce.order.grpc.UpdateBookStateGrpcService;
import com.ssu.commerce.order.persistence.OrderItemRepository;
import com.ssu.commerce.order.persistence.OrderRepository;
import com.ssu.commerce.order.supplier.OrderServiceTestDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@Slf4j
class OrderServiceTest implements OrderServiceTestDataSupplier {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UpdateBookStateGrpcService updateBookStateGrpcService;

/*
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentalBook_success() {
        RentalBookRequestDto requestDto = OrderServiceTestDataSupplier.getRentalBookRequestDto();
        Order order = OrderServiceTestDataSupplier.getOrderWithId();

        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(order);

        Order resultOrder = orderService.rentalBook(requestDto, TEST_VAL_USER_ID);

        assertEquals(order, resultOrder);
    }

    @Test
    void saveOrder_success() {
        RentalBookRequestDto requestDto = OrderServiceTestDataSupplier.getRentalBookRequestDto();
        Order savedOrder = OrderServiceTestDataSupplier.getOrderWithId();

        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(savedOrder);

        Order result = orderService.saveOrder(TEST_VAL_USER_ID, requestDto);

        assertEquals(savedOrder, result);
    }

    @Test
    void saveOrder_fail_when_order_save() {
        RentalBookRequestDto requestDto = OrderServiceTestDataSupplier.getRentalBookRequestDto();

        Exception e = new Exception("TEST EXCEPTION : save order");
        OrderFailException orderFailException = new OrderFailException("ORDER_001", "Order save error : " + e.getMessage());

        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenAnswer(invocation -> {
            throw e;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.saveOrder(TEST_VAL_USER_ID, requestDto);
        });

        assertEquals(orderFailException, resultException);

    }

    @Test
    void saveOrder_fail_when_order_item_save() {
        RentalBookRequestDto requestDto = OrderServiceTestDataSupplier.getRentalBookRequestDto();
        Order savedOrder = OrderServiceTestDataSupplier.getOrderWithId();

        Exception e = new Exception("TEST EXCEPTION : save order item");
        OrderFailException orderFailException = new OrderFailException("ORDER_001", "Order save error : " + e.getMessage());


        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(savedOrder);

        when(orderItemRepository.save(
                argThat(orderItem1 -> orderItem1.getBookId().equals(TEST_VAL_BOOK_ID) &&
                        orderItem1.getStartedAt().equals(TEST_VAL_DATE_TIME) &&
                        orderItem1.getEndAt().equals(TEST_VAL_DATE_END)
                )
        )).thenAnswer(invocation -> {
            throw e;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.saveOrder(TEST_VAL_USER_ID, requestDto);
        });

        assertEquals(orderFailException, resultException);

    }

    @Test
    void getOrderList() {
        Page<OrderListParamDto> orderListParamDtoPage = OrderServiceTestDataSupplier.getOrderListParamDtoPage();
        Page<Order> orderPage = OrderServiceTestDataSupplier.getOrderPage();

        when(orderRepository.findByUserId(TEST_VAL_USER_ID, Pageable.unpaged())).thenReturn(orderPage);

        Page<OrderListParamDto> result = orderService.getOrderList(OrderServiceTestDataSupplier.getOrderListParamDto());

        assertEquals(orderListParamDtoPage, result);

        verify(orderRepository, times(1)).findByUserId(TEST_VAL_USER_ID, Pageable.unpaged());

    }*/
}