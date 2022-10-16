package com.commerce.product.dto;

import com.commerce.global.common.Price;
import com.commerce.product.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailResponseDto {

    private Long id;
    private String name;
    private String imgPath;
    private Price price;
    private String status;
    private String compositionType;
    private List<OptionVo> options;

    public ProductDetailResponseDto(Product product) {
        id = product.getId();
        name = product.getName();
        imgPath = product.getImgPath();
        price = product.getPrice();
        status = product.getStatus().getName();
        compositionType = product.getCompositionType().getName();
    }

}
