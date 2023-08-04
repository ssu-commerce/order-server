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
@Table(name = "order_cart")
public class OrderCart {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
