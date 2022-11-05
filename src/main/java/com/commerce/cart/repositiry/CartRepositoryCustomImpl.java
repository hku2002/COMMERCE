package com.commerce.cart.repositiry;

import com.commerce.cart.domain.Cart;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.commerce.cart.domain.QCart.cart;

@RequiredArgsConstructor
public class CartRepositoryCustomImpl implements CartRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Cart findByIdAndMemberId(Long cartId, Long memberId) {
        return queryFactory
                .selectFrom(cart)
                .where(cart.id.eq(cartId)
                        , cart.member.id.eq(memberId)
                        , cart.activated.eq(true))
                .fetchFirst();
    }

    @Override
    public List<Cart> findCartsByMemberId(Long memberId, Pageable pageable) {
        return queryFactory
                .selectFrom(cart)
                .join(cart.option).fetchJoin()
                .join(cart.product).fetchJoin()
                .join(cart.item).fetchJoin()
                .where(cart.member.id.eq(memberId)
                        , cart.activated.eq(true))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    @Override
    public List<Cart> findCartsByCartIdsAndMemberId(List<Long> cartIds, Long memberId) {
        return queryFactory
                .selectFrom(cart)
                .join(cart.option).fetchJoin()
                .join(cart.product).fetchJoin()
                .join(cart.item).fetchJoin()
                .where(cart.id.in(cartIds)
                        , cart.member.id.eq(memberId)
                        , cart.activated.eq(true))
                .fetch();
    }
}
