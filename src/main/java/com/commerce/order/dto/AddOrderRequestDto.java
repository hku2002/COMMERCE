package com.commerce.order.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddOrderRequestDto {

    private List<Long> cartIds;

}
