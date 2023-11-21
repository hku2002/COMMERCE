package com.commerce.product.domain;

import com.commerce.product.domain.ItemProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemProductMappingRepository extends JpaRepository<ItemProductMapping, Long> {
    ItemProductMapping findTop1ByItemIdAndProductIdAndActivated(Long itemId, Long productId, boolean activated);
}
