package com.commerce.order.controller;

import com.commerce.global.common.CommonResponse;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import com.commerce.order.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @GetMapping("/v1/orders")
    public ResponseEntity<?> orders(PagingCommonRequestDto pagingCommonRequestDto) {
        return CommonResponse.setResponse(orderServiceImpl.findOrders(1L, pagingCommonRequestDto.getLimit(), pagingCommonRequestDto.getOffset()));
    }
}
