package com.commerce.cart.repositiry;

import com.commerce.cart.domain.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c join fetch c.option o join fetch c.product where c.userId = :userId and c.activated = true")
    List<Cart> findWithOptionAndProductByUserId(Long userId, Pageable pageable);
}
