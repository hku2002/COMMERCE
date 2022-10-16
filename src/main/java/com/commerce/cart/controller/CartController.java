package com.commerce.cart.controller;

import com.commerce.cart.dto.AddCartRequestDto;
import com.commerce.cart.service.CartServiceImpl;
import com.commerce.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    @PostMapping("/v1/cart")
    public ResponseEntity<?> addCart(AddCartRequestDto addCartRequestDto) {
        cartServiceImpl.addCart(addCartRequestDto);
        return CommonResponse.setResponse();
    }
}
