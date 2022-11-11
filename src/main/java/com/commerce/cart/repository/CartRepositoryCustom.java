package com.commerce.cart.repository;

import com.commerce.cart.domain.Cart;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CartRepositoryCustom {

    Cart findByIdAndMemberId(Long cartId, Long memberId);

    List<Cart> findCartsByMemberId(Long memberId, Pageable pageable);

    List<Cart> findCartsByCartIdsAndMemberId(List<Long> cartIds, Long memberId);
}
