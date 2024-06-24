package com.ssu.commerce.order.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_cart")
public class OrderCart {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "order_cart_id", columnDefinition = "char(36)")
    private UUID orderCartId;

    @Column(name = "user_id", columnDefinition = "char(36)")
    private UUID userId;
}
