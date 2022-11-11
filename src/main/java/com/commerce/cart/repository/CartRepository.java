package com.commerce.cart.repository;

import com.commerce.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

    Cart findTop1ByItemIdAndProductIdAndOptionIdAndActivated(Long itemId, Long productId, Long optionId, boolean activated);

}
