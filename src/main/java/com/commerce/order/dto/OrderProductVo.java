package com.commerce.order.dto;

import com.commerce.global.common.Price;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderProductVo {

    private Long id;
    private Long orderId;
    private Long productId;
    private Price price;
    private int supplyPrice;
    private int userPurchaseQuantity;

}
