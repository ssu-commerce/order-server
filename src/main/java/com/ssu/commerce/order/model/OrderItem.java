package com.ssu.commerce.order.model;

import com.ssu.commerce.order.constant.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_state")
    private OrderState orderState;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "end_at")
    private LocalDateTime endAt;
}
