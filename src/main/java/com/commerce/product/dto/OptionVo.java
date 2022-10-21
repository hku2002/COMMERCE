package com.commerce.product.dto;

import com.commerce.product.domain.Option;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionVo {
    private Long id;
    private Long itemId;
    private String name;
    private Long parentId;
    private Long productId;
    private boolean lastStage;
    private int stage;
    private int stockQuantity;

    public OptionVo(Option option) {
        id = option.getId();
        itemId = option.getItem().getId();
        name = option.getName();
        parentId = option.getParentId();
        productId = option.getProductId();
        stage = option.getStage();
        if (option.getParentId() == null) {
            lastStage = true;
            stockQuantity = option.getItem().getStockQuantity();
        } else {
            lastStage = false;
            stockQuantity = 1000;
        }
    }
}
