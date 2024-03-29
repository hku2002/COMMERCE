package com.commerce.product.domain;

import com.commerce.product.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Item findByIdAndActivated(Long itemId, boolean activated);

    List<Item> findAllByIdInAndActivated(List<Long> itemIds, boolean activated);
}
