package com.commerce.cart.dto;

import com.commerce.cart.domain.Cart;
import com.commerce.global.common.Price;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDto {

    private Long id;
    private Long productId;
    private Long itemId;
    private int quantity;
    private String productName;
    private String optionName;
    private Price price;
    private String imageUrl;

    public CartResponseDto(Cart cart) {
        id = cart.getId();
        productId = cart.getProduct().getId();
        itemId = cart.getItemId();
        quantity = cart.getUserPurchaseQuantity();
        productName = cart.getProduct().getName();
        optionName = cart.getOption().getName();
        price = cart.getPrice();
        imageUrl = cart.getProduct().getImgPath();
    }
}
