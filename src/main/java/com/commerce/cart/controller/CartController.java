package com.commerce.cart.controller;

import com.commerce.cart.dto.AddCartRequestDto;
import com.commerce.cart.service.CartServiceImpl;
import com.commerce.global.common.CommonResponse;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    @GetMapping("/v1/carts")
    public ResponseEntity<?> carts(PagingCommonRequestDto requestDto) {
        return CommonResponse.setResponse(cartServiceImpl.findCarts(requestDto));
    }

    @PostMapping("/v1/cart")
    public ResponseEntity<?> addCart(AddCartRequestDto addCartRequestDto) {
        cartServiceImpl.addCart(addCartRequestDto);
        return CommonResponse.setResponse();
    }

    @DeleteMapping("/v1/cart/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId) {
        cartServiceImpl.deleteCart(cartId);
        return CommonResponse.setResponse();
    }
}
