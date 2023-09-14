package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderCartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderCartItemRepository extends JpaRepository<OrderCartItem, UUID>{
}
