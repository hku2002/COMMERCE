package com.commerce.product.repository;

import com.commerce.product.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Option findTop1ByIdAndActivated(Long optionId, boolean activated);

    List<Option> findByProductIdAndActivated(Long productId, boolean activated);
}
