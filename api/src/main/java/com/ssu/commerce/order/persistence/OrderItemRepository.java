package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
