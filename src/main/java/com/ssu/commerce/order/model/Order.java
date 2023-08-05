package com.ssu.commerce.order.model;

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
@Table(name = "orders")
public class Order {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "ordered_at")
    private LocalDateTime orderedAt;

    @Column(name = "user_id")
    private String userId;

}
