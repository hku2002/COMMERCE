package com.commerce.order.repository;

import com.commerce.order.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.member m join fetch o.delivery where o.member.id = :memberId")
    List<Order> findWithMemberAndDeliveryByMemberId(Long memberId, Pageable pageable);

    @Query("select o from Order o join fetch o.delivery where o.id = :orderId and o.delivery.activated = true")
    Order findWithDeliveryByOrderId(Long orderId);

}
