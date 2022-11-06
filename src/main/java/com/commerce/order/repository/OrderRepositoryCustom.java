package com.commerce.order.repository;

import com.commerce.order.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findWithMemberAndDeliveryByMemberId(String userId, Pageable pageable);
    
    Order findWithDeliveryByOrderId(Long orderId);
}
