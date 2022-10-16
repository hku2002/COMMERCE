package com.commerce.order.dto;

import com.commerce.global.common.Price;
import com.commerce.order.domain.OrderProduct;
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

    public OrderProductVo (OrderProduct orderProduct) {
        this.id = orderProduct.getId();
        this.orderId = orderProduct.getOrder().getId();
        this.productId = orderProduct.getProduct().getId();
        this.price = orderProduct.getPrice();
        this.supplyPrice = orderProduct.getSupplyPrice();
        this.userPurchaseQuantity = orderProduct.getUserPurchaseQuantity();
    }

}
