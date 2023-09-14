package com.ssu.commerce.order.model;

import com.ssu.commerce.order.constant.OrderState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_cart_item")
public class OrderCartItem {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "book_id", columnDefinition = "BINARY(16)")
    private UUID bookId;

    @Column(name = "order_cart_id", columnDefinition = "BINARY(16)")
    private UUID orderCartId;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
