package com.commerce.order.domain;

import com.commerce.order.domain.Order;
import com.commerce.order.repository.OrderRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    Order findByIdAndActivated(Long id, boolean activated);
}
