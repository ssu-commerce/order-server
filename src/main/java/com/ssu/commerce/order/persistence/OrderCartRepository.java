package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderCartRepository extends JpaRepository<OrderCart, Long>, OrderCartRepositoryCustom {
    Optional<OrderCart> findByUserId(String userId);
}
