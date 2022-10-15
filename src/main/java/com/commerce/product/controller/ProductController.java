package com.commerce.product.controller;

import com.commerce.global.common.CommonResponse;
import com.commerce.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    public ResponseEntity<?> products() {
        return CommonResponse.setResponse(productServiceImpl.findProducts());
    }
}
