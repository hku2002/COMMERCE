package com.commerce.cart.dto;

import com.commerce.cart.domain.Cart;
import com.commerce.global.common.Price;
import com.commerce.product.domain.Product.DisplayStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class CartResponseDto {

    private Long id;
    private Long productId;
    private Long itemId;
    private int quantity;
    private String productName;
    private String optionName;
    private Price price;
    private String imageUrl;
    private DisplayStatus status;

}
