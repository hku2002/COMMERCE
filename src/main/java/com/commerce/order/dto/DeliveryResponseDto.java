package com.commerce.order.dto;

import com.commerce.global.common.Address;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.commerce.delivery.domain.Delivery.DeliveryStatus;

@Getter
@Setter
@Builder
public class DeliveryResponseDto {

    private Long id;
    private Address address;
    private DeliveryStatus status;

}
