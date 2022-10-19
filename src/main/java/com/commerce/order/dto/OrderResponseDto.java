package com.commerce.order.dto;

import com.commerce.delivery.domain.Delivery;
import com.commerce.order.domain.Order;
import lombok.Getter;
import lombok.Setter;

import static com.commerce.order.domain.Order.OrderStatus;

@Getter
@Setter
public class OrderResponseDto {

    private Long id;
    private Long memberId;
    private String name;
    private OrderStatus status;
    private int totalPrice;
    private Delivery delivery;

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.memberId = order.getMember().getId();
        this.name = order.getName();
        this.status = order.getStatus();
        this.totalPrice = order.getTotalPrice();
        this.delivery = order.getDelivery();
    }

}
