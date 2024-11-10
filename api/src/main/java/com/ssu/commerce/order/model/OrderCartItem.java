package com.ssu.commerce.order.model;


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
@Table(name = "order_cart_item")
public class OrderCartItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-char")
    @Column(name = "order_cart_item_id", columnDefinition = "char(36)")
    private UUID orderCartItemId;

    @Type(type = "uuid-char")
    @Column(name = "book_id", columnDefinition = "char(36)")
    private UUID bookId;

    @Type(type = "uuid-char")
    @Column(name = "order_cart_id", columnDefinition = "char(36)")
    private UUID orderCartId;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
