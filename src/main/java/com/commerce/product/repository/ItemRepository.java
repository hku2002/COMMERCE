package com.commerce.product.repository;

import com.commerce.product.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join fetch i.productProductMapping where i.id = :itemId")
    Item findByIdWithItemProductMapping(Long itemId);
}
