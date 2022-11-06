package com.commerce.product.repository;

import com.commerce.product.domain.Option;

import java.util.List;

public interface OptionRepositoryCustom {

    List<Option> findWithItemByProductIdAndActivated(Long productId, boolean activated);
}
