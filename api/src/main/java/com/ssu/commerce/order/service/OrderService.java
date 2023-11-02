package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderListParamDto;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.exception.OrderFailException;
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

    public Order createOrder(List<CreateOrderRequestDto> requestDto, String accessToken, UUID userId) {

        /*
         *   TODO 도서 조회 후 빌릴 수 있는지 확인 못하면 error
         *    현재 토큰 대신 userId로 보냄 이후에 수정 필요
         *    rentalBook 메소드는 하나의 책에 대한 주문만 처리
         *    장바구니(책 여러개)에 대한 처리는 다른 메소드에서 진행
         */

        updateBookStateGrpcService.sendMessageToUpdateBookState(requestDto, accessToken, BookState.LOAN_PROCESSING);

        /*
         *   TODO 결제 API 연동
         *    현재는 임시 처리, 연동 후엔 proto 정의해서 사용
         */

        boolean paymentFail = false;
        if (paymentFail) {
            updateBookStateGrpcService.sendMessageToUpdateBookState(requestDto, accessToken, BookState.REGISTERED);
        }

        Order order = saveOrder(userId, accessToken, requestDto);

        updateBookStateGrpcService.sendMessageToUpdateBookState(requestDto, accessToken, BookState.LOAN);

        return order;
    }

    @Transactional
    Order saveOrder(UUID userId, String accessToken, List<CreateOrderRequestDto> requestDto) {
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
            /*
             * TODO 결제 롤백 연동
             */
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
