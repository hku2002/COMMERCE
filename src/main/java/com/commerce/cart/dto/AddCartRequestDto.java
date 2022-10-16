package com.commerce.cart.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    private Long itemId;

    @NotNull
    private int quantity;

}
