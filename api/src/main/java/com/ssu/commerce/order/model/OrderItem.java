package com.ssu.commerce.order.model;

import com.ssu.commerce.order.constant.OrderState;
import com.ssu.commerce.order.dto.request.CreateOrderInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

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
    @Type(type = "uuid-char")
    @Column(name = "order_item_id", columnDefinition = "char(36)")
    private UUID orderItemId;

    @Type(type = "uuid-char")
    @Column(name = "book_id", columnDefinition = "char(36)")
    private UUID bookId;

    @Type(type = "uuid-char")
    @Column(name = "order_id", columnDefinition = "char(36)")
    private UUID orderId;

    @Column(name = "order_state")
    private OrderState orderState;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;

    public OrderItem(CreateOrderInfoDto req, UUID orderId) {
        bookId = req.getBookId();
        orderState = OrderState.REQUESTED;
        startedAt = req.getStartedAt();
        endAt = req.getEndAt();
        this.orderId = orderId;
    }

    public void updateOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}
