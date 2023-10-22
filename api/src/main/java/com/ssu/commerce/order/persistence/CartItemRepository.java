package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CartItemRepository extends JpaRepository<OrderCartItem, UUID>{
}
