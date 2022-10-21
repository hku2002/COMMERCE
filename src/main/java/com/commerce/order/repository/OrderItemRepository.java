package com.commerce.order.repository;

import com.commerce.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderIdAndActivated(Long orderId, boolean activated);
}
