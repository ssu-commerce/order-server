package com.ssu.commerce.order.model;


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
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "order_id", columnDefinition = "char(36)")
    private UUID orderId;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Column(name = "user_id", columnDefinition = "BINARY(16)")
    private UUID userId;

}
