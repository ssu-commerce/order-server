package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.dto.param.SaveOrderParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.request.PaymentRequest;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.dto.response.PaymentResponse;
import com.ssu.commerce.order.exception.OrderErrorCode;
import com.ssu.commerce.order.exception.OrderFailException;
import com.ssu.commerce.order.feign.PaymentFeignClient;
import com.ssu.commerce.order.grpc.BookState;
import com.ssu.commerce.order.grpc.UpdateBookStateGrpcService;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import com.ssu.commerce.order.persistence.OrderItemRepository;
import com.ssu.commerce.order.persistence.OrderRepository;
import com.ssu.commerce.order.supplier.OrderTestDataSupplier;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class OrderServiceTest implements OrderTestDataSupplier {

    @Spy
    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private UpdateBookStateGrpcService updateBookStateGrpcService;

    @Mock
    private PaymentFeignClient paymentFeignClient;

    @Test
    void creteOrder_success() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();
        Order savedOrder = OrderTestDataSupplier.getOrder();
        PaymentResponse paymentResponse = OrderTestDataSupplier.getPaymentResponse();
        SsuCommerceAuthenticatedPrincipal principal = OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        SaveOrderParamDto saveOrderParamDto = OrderTestDataSupplier.getSaveOrderParamDto(requestDto.getOrderInfo());

        doReturn(savedOrder).when(orderService).saveOrder(saveOrderParamDto);
        doReturn(paymentResponse).when(orderService).requestPayment(requestDto, principal);

        Order resultOrder = orderService.createOrder(requestDto, principal);

        assertEquals(savedOrder, resultOrder);
        verify(orderService).saveOrder(saveOrderParamDto);
        verify(orderService).requestPayment(requestDto, principal);
    }

    @Test
    void creteOrder_fail_grpc_error_LoanProcessing() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();
        SsuCommerceAuthenticatedPrincipal principal = OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();
        Exception grpcException = new Exception("TEST EXCEPTION");

        when(updateBookStateGrpcService.sendMessageToUpdateBookState(
                requestDto.getOrderInfo(), TEST_VAL_ACCESS_TOKEN, BookState.LOAN_PROCESSING
        )).thenAnswer(invocation -> {
            throw grpcException;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.createOrder(requestDto, principal);
        });

        assertEquals(grpcException, resultException);
    }

    @Test
    void creteOrder_fail_grpc_error_Loan() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();
        PaymentResponse paymentResponse = OrderTestDataSupplier.getPaymentResponse();
        SsuCommerceAuthenticatedPrincipal principal = OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal();

        Exception grpcException = new Exception("TEST EXCEPTION");

        doReturn(paymentResponse).when(orderService).requestPayment(requestDto, principal);
        when(updateBookStateGrpcService.sendMessageToUpdateBookState(
                requestDto.getOrderInfo(), TEST_VAL_ACCESS_TOKEN, BookState.LOAN_PROCESSING
        )).thenAnswer(invocation -> null);

        when(updateBookStateGrpcService.sendMessageToUpdateBookState(
                requestDto.getOrderInfo(), TEST_VAL_ACCESS_TOKEN, BookState.LOAN
        )).thenAnswer(invocation -> {
            throw grpcException;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.createOrder(requestDto, principal);
        });

        assertEquals(grpcException, resultException);
    }

    @Test
    void requestPayment_success() {

        when(paymentFeignClient.requestPayment(any(PaymentRequest.class))).thenReturn(OrderTestDataSupplier.getPaymentResponse());

        PaymentResponse response = orderService.requestPayment(
                OrderTestDataSupplier.getCreateOrderRequestDto(), OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal());
        assertEquals(TEST_VAL_PAYMENT_ID, response.getTransactionId());
    }

    @Test
    void requestPayment_fail() {
        Exception paymentException = new Exception("TEST EXCEPTION : payment");
        OrderFailException orderFailException = new OrderFailException(OrderErrorCode.PAYMENT, "Order save error : " + paymentException.getMessage());

        when(paymentFeignClient.requestPayment(any(PaymentRequest.class))).thenAnswer(invocation -> {
            throw paymentException;
        });

        Exception resultException = assertThrows(OrderFailException.class, () -> {
            orderService.requestPayment(
                    OrderTestDataSupplier.getCreateOrderRequestDto(), OrderTestDataSupplier.getSsuCommerceAuthenticatedPrincipal());
        });

        assertEquals(orderFailException, resultException);
    }

    @Test
    void saveOrder_success() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();
        Order savedOrder = OrderTestDataSupplier.getOrder();

        when(orderRepository.save(
                argThat(order -> order != null &&
                        order.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(savedOrder);

        Order result = orderService.saveOrder(
                OrderTestDataSupplier.getSaveOrderParamDto(requestDto.getOrderInfo())
        );

        assertEquals(savedOrder, result);
        verify(orderRepository).save(
                argThat(order -> order != null &&
                        order.getUserId().equals(TEST_VAL_USER_ID))
        );
    }

    @Test
    void saveOrder_fail_when_order_save() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();

        Exception exception = new Exception("TEST EXCEPTION : save order");
        OrderFailException orderFailException = new OrderFailException(OrderErrorCode.SAVE, "Order save error : " + exception.getMessage());

        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenAnswer(invocation -> {
            throw exception;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.saveOrder(OrderTestDataSupplier.getSaveOrderParamDto(requestDto.getOrderInfo()));
        });

        assertEquals(orderFailException, resultException);

    }

    @Test
    void saveOrder_fail_when_order_item_save() {
        CreateOrderRequestDto requestDto = OrderTestDataSupplier.getCreateOrderRequestDto();
        Order savedOrder = OrderTestDataSupplier.getOrder();

        Exception exception = new Exception("TEST EXCEPTION : save order item");
        OrderFailException orderFailException = new OrderFailException(OrderErrorCode.SAVE, "Order save error : " + exception.getMessage());

        when(orderRepository.save(
                argThat(order1 -> order1.getUserId().equals(TEST_VAL_USER_ID))
        )).thenReturn(savedOrder);

        when(orderItemRepository.saveAll(
                argThat(list -> list.equals(
                        requestDto.getOrderInfo().stream().map(req ->
                                new OrderItem(req, TEST_VAL_USER_ID)).collect(Collectors.toList())
                ))
        )).thenAnswer(invocation -> {
            throw exception;
        });

        Exception resultException = assertThrows(Exception.class, () -> {
            orderService.saveOrder(OrderTestDataSupplier.getSaveOrderParamDto(requestDto.getOrderInfo()));
        });

        assertEquals(orderFailException, resultException);

    }

    @Test
    void getOrderList() {
        Page<OrderListParamDto> orderListParamDtoPage = OrderTestDataSupplier.getOrderListParamDtoPage();
        Page<Order> orderPage = OrderTestDataSupplier.getOrderPage();

        when(orderRepository.findByUserId(TEST_VAL_USER_ID, Pageable.unpaged())).thenReturn(orderPage);

        Page<OrderListParamDto> result = orderService.getOrderList(OrderTestDataSupplier.getGetOrderListParamDto());

        assertEquals(orderListParamDtoPage, result);

        verify(orderRepository, times(1)).findByUserId(TEST_VAL_USER_ID, Pageable.unpaged());

    }

    @Test
    void updateOrderItem_success() {
        OrderItem orderItem = OrderTestDataSupplier.getOrderItem();

        when(orderItemRepository.findById(TEST_VAL_ORDER_ITEM_ID)).thenReturn(Optional.ofNullable(orderItem));

        UUID updateItemId = orderService.updateOrderItem(TEST_VAL_ORDER_ITEM_ID);

        assertEquals(TEST_VAL_ORDER_ITEM_ID, updateItemId);
        verify(orderItemRepository).findById(TEST_VAL_ORDER_ITEM_ID);
    }

    @Test
    void updateOrderItem_fail_NotFoundException() {
        NotFoundException notFoundException = new NotFoundException(
                String.format("orderItem not found; orderItemId=%s", TEST_VAL_ORDER_ITEM_ID),
                "ORDER_ITEM_001"
        );

        when(orderItemRepository.findById(TEST_VAL_ORDER_ITEM_ID)).thenReturn(Optional.empty());

        NotFoundException resultException = assertThrows(NotFoundException.class, () -> {
            orderService.updateOrderItem(TEST_VAL_ORDER_ITEM_ID);
        });

        assertEquals(notFoundException.getMessage(), resultException.getMessage());
    }
}
