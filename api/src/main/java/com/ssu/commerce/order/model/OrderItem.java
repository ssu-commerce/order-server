package com.ssu.commerce.order.model;

import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.request.CreateOrderRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "book_id", columnDefinition = "BINARY(16)")
    private UUID bookId;

    @Column(name = "order_id", columnDefinition = "BINARY(16)")
    private UUID orderId;

    @Column(name = "order_state")
    private OrderState orderState;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    public OrderItem(CreateOrderRequestDto req, UUID orderId) {
        bookId = req.getBookId();
        orderState = OrderState.REGISTERED;
        startedAt = req.getStartedAt();
        endAt = req.getEndAt();
        this.orderId = orderId;
    }

    public void updateOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
