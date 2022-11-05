package com.commerce.order.repository;

import com.commerce.order.domain.Order;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.commerce.delivery.domain.QDelivery.*;
import static com.commerce.order.domain.QOrder.*;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findWithMemberAndDeliveryByMemberId(Long memberId, Pageable pageable) {
        return queryFactory
                .selectFrom(order)
                .join(order.member).fetchJoin()
                .join(order.delivery).fetchJoin()
                .where(order.member.id.eq(memberId)
                        , order.activated.eq(true)
                        , order.member.activated.eq(true)
                        , order.delivery.activated.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public Order findWithDeliveryByOrderId(Long orderId) {
        return queryFactory.selectFrom(order)
                .join(order.delivery).fetchJoin()
                .where(order.id.eq(orderId)
                        , order.activated.eq(true)
                        , order.delivery.activated.eq(true))
                .fetchFirst();
    }
}
