package com.commerce.cart.repositiry;

import com.commerce.cart.domain.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>, CartRepositoryCustom {

    Cart findTop1ByItemIdAndProductIdAndOptionIdAndActivated(Long itemId, Long productId, Long optionId, boolean activated);

}
