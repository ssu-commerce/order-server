package com.ssu.commerce.order.persistence;

import com.ssu.commerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
