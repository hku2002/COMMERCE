package com.commerce.cart.repositiry;

import com.commerce.cart.domain.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select c from Cart c " +
           "join fetch c.option o " +
           "join fetch c.product " +
           "join fetch c.item " +
           "where c.member.id = :memberId " +
           "and c.activated = true")
    List<Cart> findWithOptionAndProductAndItemByMemberId(Long memberId, Pageable pageable);

    @Query("select c from Cart c where c.id = :id and c.member.id = :memberId and c.activated = true")
    Cart findByIdAndMemberId(Long id, Long memberId);

    Cart findTop1ByItemIdAndProductIdAndOptionIdAndActivated(Long itemId, Long productId, Long optionId, boolean activated);

    @Query("select c from Cart c " +
           "join fetch c.option o " +
           "join fetch c.item " +
           "join fetch c.product " +
           "where c.id in :cartIds " +
           "and c.activated = true")
    List<Cart> findWithOptionAndItem(List<Long> cartIds);
}
