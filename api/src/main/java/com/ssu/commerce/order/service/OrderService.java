package com.ssu.commerce.order.service;

import com.ssu.commerce.core.error.NotFoundException;
import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.param.GetOrderCartListParamDto;
import com.ssu.commerce.order.dto.mapper.*;
import com.ssu.commerce.order.dto.param.RegisterBookToCartParamDto;
import com.ssu.commerce.order.dto.param.SelectOrderCartParamDto;
import com.ssu.commerce.order.dto.request.RentalBookRequestDto;
import com.ssu.commerce.order.dto.request.ReturnBookRequestDto;
import com.ssu.commerce.order.dto.response.OrderListParamDto;
import com.ssu.commerce.order.exception.OrderFailException;
import com.ssu.commerce.order.grpc.GetAvailableBookInfoGrpcService;
import com.ssu.commerce.order.model.Order;
import com.ssu.commerce.order.model.OrderCart;
import com.ssu.commerce.order.model.OrderCartItem;
import com.ssu.commerce.order.model.OrderItem;
import com.ssu.commerce.order.persistence.OrderCartItemRepository;
import com.ssu.commerce.order.persistence.OrderCartRepository;
import com.ssu.commerce.order.persistence.OrderItemRepository;
import com.ssu.commerce.order.persistence.OrderRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderCartRepository orderCartRepository;
    private final OrderCartItemRepository orderCartItemRepository;
    private final GetAvailableBookInfoGrpcService getAvailableBookInfoGrpcService;

    public void createOrder() {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call (성엽)
         *   도서 상세 내용을 조회한다.
         *   -> request (bookId)
         *   -> response (bookId, 대출가능여부, 대여가능기간, 대여 장소(책 주인 위치), 대여비, 보증금, 배송 flag - 픽업 or 배송, 배송비 고정)
         *  2. 계좌(포인트) 조회 API call (상일)
         *   -> response (포인트 얼마있는지)
         *  3. 결제 정보 생성 (배송 flag - 픽업 or 배송) (주해)
         *   잔고 확인, 잔고 없으면 rollback
         *   -> response (orderId)
         *   -> order 에 flag 를 둬서 실패시에 fail ... 상태 남겨두기
         *  4. 결제 진행 (주해)
         *    2-1. 계좌(포인트) 수정 API call (상일)
         *      -> request (포인트 +/-)
         *  5. history 남기기
//         *  1-2. 책 주인에게 도서 대여 가능 여부 check (도서 - 성엽)
//         *    -> ok -> 상태 wait 로 변경 (주문 대기 상태면 여러 고객이 주문대기를 걸 수 있다.)
         */

        // TODO return 주문 정보 (to front)
        //  transactionId : ORDER-1
    }

    // TODO (front -> order_confirmOrder)
    public void confirmOrder() {
        /*
         * TODO
         *  1. 책 주인이 주문 대기 queue 에서 선택하여 승인
         *  3. 결제 상태 변경
         *  3. 도서 배송 API call (order -> delivery) (현우)
         *   -> request (orderLocation, bookId)
         *   -> response ()
         *  5. history 남기기
         */
    }

//    public RentalBookResponseDto rentalBook(
//            RentalBookRequestDto requestDto
//    ) {
//        /*
//
//         * TODO
//         *  1. 책 주인이 주문 대기 queue 에서 선택하여 승인 (confirmOrder)
//         *  2. 도서 대여비 결제 (주해)
//         *   대여할 도서의 대여비를 결제한다.
//         *   -> request (userId, bookId, 대여비)
//         *   -> response (결제 내역)
//         *  3. 도서 배송 API call (order -> delivery) (현우)
//         *   -> request (orderLocation, bookId)
//         *   -> response ()
//         */
//        return RentalBookResponseDto.builder().build();
//    }

    @Transactional
    public UUID returnBook(
            ReturnBookRequestDto responseDto
    ) {
        /*
         * TODO 구현이 필요합니다.
         *  1. 도서 조회 API call
         *  2. 도서 반납
         *  3. 도서 배송 API call
         */

        OrderItem orderItem = orderItemRepository.findById(responseDto.getOrderItemId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("orderItem not found; orderItemId=%s", responseDto.getOrderItemId()),
                        "ORDER_ITEM_001"
                ));

        orderItem.setOrderState(OrderState.RETURNED);
        orderItem = orderItemRepository.save(orderItem);

        return orderItem.getId();
    }

    public Order rentalBook(RentalBookRequestDto requestDto, UUID userId) {

        /*
         *   TODO 도서 조회 후 빌릴 수 있는지 확인 못하면 error
         *    현재 토큰 대신 userId로 보냄 이후에 수정 필요
         *    rentalBook 메소드는 하나의 책에 대한 주문만 처리
         *    장바구니(책 여러개)에 대한 처리는 다른 메소드에서 진행
         */

        getAvailableBookInfoGrpcService.sendMessageToGetRentalBook(requestDto.getBookId().toString(), userId.toString());

        /*
         *   TODO 결제 API 연동
         *    현재는 임시 처리, 연동 후엔 proto 정의해서 사용
         */

        boolean paymentFail = false;
        if (paymentFail) {
            getAvailableBookInfoGrpcService.sendMessageToRollBackRental(requestDto.getBookId().toString(), userId.toString());
        }

        Order order = saveOrder(userId, requestDto);

        getAvailableBookInfoGrpcService.sendMessageToCompleteRentalBook(requestDto.getBookId().toString(), userId.toString());

        return order;
    }

    @Transactional
    Order saveOrder(UUID userId, RentalBookRequestDto requestDto) {
        try {
            Order order = orderRepository.save(
                    Order.builder()
                            .orderedAt(LocalDateTime.now())
                            .userId(userId)
                            .build()
            );

            orderItemRepository.save(
                    OrderItemListMapper.INSTANCE.map(
                            requestDto,
                            order.getId()
                    )
            );

            return order;
        } catch (Exception e) {
            log.error("Order save error : " + e.getMessage());
            /*
             * TODO 결제 롤백 연동
             */
            getAvailableBookInfoGrpcService.sendMessageToGetRentalBook(requestDto.getBookId().toString(), userId.toString());
            throw new OrderFailException("ORDER_001", "Order save error : " + e.getMessage());
        }
    }

    @Transactional
    public UUID approveRental(UUID id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException(
                        String.format("order not found; orderId=%s", id),
                        "ORDER_ITEM_001"
                ));

        orderItem.setOrderState(OrderState.APPROVED);
        orderItemRepository.save(orderItem);

        /*
            TODO 배송 관련 처리
         */

        return orderItem.getId();
    }

    @Transactional
    public UUID rejectRental(UUID id) {
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("order not found; orderId=%s", id),
                        "ORDER_ITEM_001"
                ));

        orderItem.setOrderState(OrderState.REJECTED);
        orderItemRepository.save(orderItem);

        return orderItem.getId();
    }

    @Transactional
    public UUID addBookToCart(
            @NonNull @Valid RegisterBookToCartParamDto paramDto,
            UUID userId
    ) {

        OrderCart orderCart = orderCartRepository.findByUserId(userId)
                .orElseGet(() ->
                        orderCartRepository.save(
                                OrderCart.builder()
                                        .userId(userId)
                                        .build()
                        )
                );

        return orderCartItemRepository.save(
                OrderCartItem.builder()
                        .bookId(paramDto.getBookId())
                        .orderCartId(orderCart.getId())
                        .addedAt(LocalDateTime.now())
                        .build()
        ).getId();
    }

    @Transactional
    public UUID deleteBookFromCart(UUID id) {
        OrderCart orderCart = orderCartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("orderCart not found; cartId=%s", id),
                        "ORDER_CART_001"
                ));
        orderCartRepository.delete(orderCart);
        return orderCart.getId();
    }

    public Page<SelectOrderCartParamDto> getCartItemList(
            @NonNull final GetOrderCartListParamDto paramDto
    ) {

        return orderCartRepository.selectOrderCartPage(
                SelectOrderCartListParamDtoMapper.INSTANCE.map(paramDto),
                paramDto.getPageable()
        ).map(SelectOrderCartParamDtoMapper.INSTANCE::map);
    }

    public Page<OrderListParamDto> getOrderList(GetOrderListParamDto paramDto) {

        return orderRepository.findByUserId(
                paramDto.getUserId(),
                paramDto.getPageable()
        ).map(order -> new OrderListParamDto(order));
    }
}
