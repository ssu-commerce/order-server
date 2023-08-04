package com.ssu.commerce.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_cart_item")
public class OrderCartItem {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "order_cart_id")
    private String orderCartId;

    @Column(name = "book_id")
    private String book_id;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
