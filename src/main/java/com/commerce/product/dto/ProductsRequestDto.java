package com.commerce.product.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductsRequestDto {
    @Builder.Default
    private int limit = 0;
    @Builder.Default
    private int offset = 10;
}
