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
    private int stage;

    public OptionVo(Option option) {
        id = option.getId();
        itemId = option.getItemId();
        name = option.getName();
        parentId = option.getParentId();
        productId = option.getProductId();
        stage = option.getStage();
    }
}
