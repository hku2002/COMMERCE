package com.commerce.order.repository;

import com.commerce.order.domain.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepositoryCustom {

    List<Order> findWithMemberAndDeliveryByMemberId(Long memberId, Pageable pageable);
    
    Order findWithDeliveryByOrderId(Long orderId);
}
