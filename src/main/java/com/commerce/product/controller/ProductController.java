package com.commerce.product.controller;

import com.commerce.global.common.CommonResponse;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import com.commerce.product.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/v1/products")
    public ResponseEntity<?> products(PagingCommonRequestDto productsRequestDto) {
        return CommonResponse.setResponse(productServiceImpl.findProducts(productsRequestDto));
    }

    @GetMapping("/v1/product/{id}")
    public ResponseEntity<?> product(@PathVariable Long id) {
        return CommonResponse.setResponse(productServiceImpl.findProduct(id));
    }
}
