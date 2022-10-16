package com.commerce.product.repository;

import com.commerce.product.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findTop1ByIdAndActivated(Long itemId, boolean activated);
}
