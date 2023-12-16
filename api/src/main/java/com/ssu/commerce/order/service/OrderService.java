package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.request.PaymentRequest;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.dto.response.PaymentResponse;
import com.ssu.commerce.order.exception.OrderFailException;
import com.ssu.commerce.order.feign.PaymentFeignClient;
import com.ssu.commerce.order.grpc.BookState;
import com.ssu.commerce.order.grpc.UpdateBookStateGrpcService;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderItem;
import com.ssu.commerce.order.persistence.OrderItemRepository;
import com.ssu.commerce.order.persistence.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UpdateBookStateGrpcService updateBookStateGrpcService;
    private final PaymentFeignClient paymentFeignClient;

    @Transactional
    public UUID updateOrderItem(UUID orderItemId) {
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("orderItem not found; orderItemId=%s", orderItemId),
                        "ORDER_ITEM_001"
                ));

        orderItem.updateOrderState(OrderState.RETURNED);

        return orderItem.getId();
    }

    public Order createOrder(List<CreateOrderInfoDto> orderInfoDto, String accessToken, UUID userId, UUID receiverId) {

        /*
         *  TODO retry 정책 논의 필요
         */

        updateBookStateGrpcService.sendMessageToUpdateBookState(orderInfoDto, accessToken, BookState.LOAN_PROCESSING);

        PaymentResponse paymentResponse = processPaymentRequest(userId, receiverId, orderInfoDto, accessToken);


        Order order = saveOrder(userId, accessToken, orderInfoDto, paymentResponse.getTransactionId());

        updateBookStateGrpcService.sendMessageToUpdateBookState(orderInfoDto, accessToken, BookState.LOAN);

        return order;
    }

    PaymentResponse processPaymentRequest(UUID userId, UUID receiverId, List<CreateOrderInfoDto> requestDto, String accessToken) {
        try{
            return paymentFeignClient.requestPayment(
                    PaymentRequest.builder()
                            .senderId(userId)
                            .receiverId(receiverId)
                            .amount(
                                    requestDto.stream().mapToLong(CreateOrderInfoDto::getPrice).sum()
                            )
                            .build()
            );
        } catch (Exception e) {
            log.error("Order save error : " + e.getMessage());
            updateBookStateGrpcService.sendMessageToUpdateBookState(requestDto, accessToken, BookState.REGISTERED);

            throw new OrderFailException("ORDER_002", "Payment error : " + e.getMessage());
        }
    }

    @Transactional
    Order saveOrder(UUID userId, String accessToken, List<CreateOrderInfoDto> requestDto, Long paymentId) {
        try {
            Order order = orderRepository.save(
                    Order.builder()
                            .orderedAt(LocalDateTime.now())
                            .userId(userId)
                            .build()
            );

            orderItemRepository.saveAll(
                    requestDto.stream().map(req ->
                            new OrderItem(req, userId)).collect(Collectors.toList())
            );

            return order;
        } catch (Exception e) {
            log.error("Order save error : " + e.getMessage());

            paymentFeignClient.cancelPayment(paymentId);
            updateBookStateGrpcService.sendMessageToUpdateBookState(requestDto, accessToken, BookState.REGISTERED);
            throw new OrderFailException("ORDER_001", "Order save error : " + e.getMessage());
        }
    }

    public Page<OrderListParamDto> getOrderList(GetOrderListParamDto paramDto) {

        return orderRepository.findByUserId(
                paramDto.getUserId(),
                paramDto.getPageable()
        ).map(OrderListParamDto::new);
    }
}
