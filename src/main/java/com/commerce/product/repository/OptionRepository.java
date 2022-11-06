package com.commerce.product.repository;

import com.commerce.product.domain.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long>, OptionRepositoryCustom {
    Option findTop1ByIdAndActivated(Long optionId, boolean activated);
}
