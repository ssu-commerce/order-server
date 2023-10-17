package com.ssu.commerce.order.service;

import com.ssu.commerce.order.dto.mapper.GetOrderListParamDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.persistence.OrderRepository;
import com.ssu.commerce.order.supplier.OrderServiceTestDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class OrderServiceTest implements OrderServiceTestDataSupplier {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void rentalBook() {
    }

    @Test
    void saveOrder_Success() {

    }

    @Test
    void getOrderList() {
        Page<OrderListParamDto> orderListParamDtoPage = OrderServiceTestDataSupplier.getOrderListParamDtoPage();
        Page<Order> orderPage = OrderServiceTestDataSupplier.getOrderPage();

        when(orderRepository.findByUserId(TEST_VAL_USER_ID, Pageable.unpaged())).thenReturn(orderPage);

        Page<OrderListParamDto> result = orderService.getOrderList(OrderServiceTestDataSupplier.getOrderListParamDto());

        assertEquals(orderListParamDtoPage, result);

        verify(orderRepository, times(1)).findByUserId(TEST_VAL_USER_ID, Pageable.unpaged());

    }
}