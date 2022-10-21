package com.commerce.product.repository;

import com.commerce.product.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findTop1ByIdAndActivated(Long optionId, boolean activated);

    @Query("select o from Option o join fetch o.item where o.productId = :productId and o.activated = :activated")
    List<Option> findWithItemByProductIdAndActivated(Long productId, boolean activated);
}
