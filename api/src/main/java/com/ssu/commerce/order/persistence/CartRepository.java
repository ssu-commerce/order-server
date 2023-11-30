package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<OrderCart, UUID> {
    Optional<OrderCart> findByUserId(UUID userId);
}
