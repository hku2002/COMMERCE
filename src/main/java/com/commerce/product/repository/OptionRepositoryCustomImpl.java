package com.commerce.product.repository;

import com.commerce.product.domain.Option;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.commerce.product.domain.QOption.option;

@RequiredArgsConstructor
public class OptionRepositoryCustomImpl implements OptionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Option> findWithItemByProductIdAndActivated(Long productId, boolean activated) {
        return queryFactory.selectFrom(option)
                .join(option.item).fetchJoin()
                .where(option.productId.eq(productId)
                        , option.activated.eq(activated)
                        , option.item.activated.eq(activated))
                .fetch();
    }
}
