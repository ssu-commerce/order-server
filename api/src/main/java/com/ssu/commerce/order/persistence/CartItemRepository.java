package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderCartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<OrderCartItem, UUID>{
    Page<OrderCartItem> findByOrderCartId(UUID orderCartId, Pageable pageable);
}
