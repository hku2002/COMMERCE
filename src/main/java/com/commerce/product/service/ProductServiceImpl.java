package com.commerce.product.service;

import com.commerce.product.domain.Product;
import com.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    /**
     * 전시 목록 조회
     * param
     */
    public List<Product> findByDisplayCode(String displayCode) {
        return null;
    }
}
