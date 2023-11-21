package com.commerce.product.domain;

import com.commerce.product.domain.Option;
import com.commerce.product.repository.OptionRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long>, OptionRepositoryCustom {
    Option findTop1ByIdAndActivated(Long optionId, boolean activated);
}
