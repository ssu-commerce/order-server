package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.core.security.user.SsuCommerceAuthenticatedPrincipal;
import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.param.SaveOrderParamDto;
import com.ssu.commerce.order.dto.param.UpdateBookStateParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
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

        return orderItem.getOrderItemId();
    }

    public Order createOrder(CreateOrderRequestDto requestDto, SsuCommerceAuthenticatedPrincipal principal) {

        /*
         *  TODO retry 정책 논의 필요
         */

        List<CreateOrderInfoDto> orderInfoDto = requestDto.getOrderInfo();
        String accessToken = principal.getAccessToken();

        updateBookStateGrpcService.sendMessageToUpdateBookState(
                UpdateBookStateParamDto.builder()
                        .createOrderInfoDto(orderInfoDto)
                        .accessToken(accessToken)
                        .bookState(BookState.LOAN_PROCESSING)
                        .build());

        PaymentResponse paymentResponse = requestPayment(requestDto, principal);


        Order order = saveOrder(
                SaveOrderParamDto.builder()
                        .userId(principal.getUserId())
                        .accessToken(accessToken)
                        .requestDto(orderInfoDto)
                        .paymentId(paymentResponse.getTransactionId())
                        .build());

        updateBookStateGrpcService.sendMessageToUpdateBookState(
                UpdateBookStateParamDto.builder()
                        .createOrderInfoDto(orderInfoDto)
                        .accessToken(accessToken)
                        .bookState(BookState.LOAN)
                        .build());

        return order;
    }

    PaymentResponse requestPayment(CreateOrderRequestDto requestDto, SsuCommerceAuthenticatedPrincipal principal) {
        try{
            return paymentFeignClient.requestPayment(
                    PaymentRequest.builder()
                            .senderId(principal.getUserId())
                            .receiverId(requestDto.getReceiverId())
                            .amount(
                                    requestDto.getOrderInfo()
                                            .stream().mapToLong(CreateOrderInfoDto::getPrice).sum()
                            )
                            .build()
            );
        } catch (Exception e) {
            log.error("Payment error : " + e.getMessage());
            updateBookStateGrpcService.sendMessageToUpdateBookState(
                    UpdateBookStateParamDto.builder()
                            .createOrderInfoDto(requestDto.getOrderInfo())
                            .accessToken(principal.getAccessToken())
                            .bookState(BookState.REGISTERED)
                            .build());

            throw new OrderFailException(OrderErrorCode.PAYMENT, "Payment error : " + e.getMessage());
        }
    }

    @Transactional
    Order saveOrder(SaveOrderParamDto saveOrderParamDto) {
        try {
            Order order = orderRepository.save(
                    Order.builder()
                            .orderedAt(LocalDateTime.now())
                            .userId(saveOrderParamDto.getUserId())
                            .build()
            );

            orderItemRepository.saveAll(
                    saveOrderParamDto.getRequestDto().stream().map(req ->
                            new OrderItem(req, saveOrderParamDto.getUserId())).collect(Collectors.toList())
            );

            return order;
        } catch (Exception e) {
            log.error("Order save error : " + e.getMessage());

            paymentFeignClient.cancelPayment(saveOrderParamDto.getPaymentId());

            updateBookStateGrpcService.sendMessageToUpdateBookState(
                    UpdateBookStateParamDto.builder()
                            .createOrderInfoDto(saveOrderParamDto.getRequestDto())
                            .accessToken(saveOrderParamDto.getAccessToken())
                            .bookState(BookState.REGISTERED)
                            .build());

            throw new OrderFailException(OrderErrorCode.SAVE, "Order save error : " + e.getMessage());
        }
    }

    public Page<OrderListParamDto> getOrderList(GetOrderListParamDto paramDto) {

        return orderRepository.findByUserId(
                paramDto.getUserId(),
                paramDto.getPageable()
        ).map(OrderListParamDto::new);
    }
}
