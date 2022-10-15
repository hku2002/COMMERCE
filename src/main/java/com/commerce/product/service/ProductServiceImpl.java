package com.commerce.product.service;

import com.commerce.product.domain.Product;
import com.commerce.product.dto.ProductResponseDto;
import com.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.product.domain.Product.DisplayStatus.DISPLAY;
import static com.commerce.product.domain.Product.DisplayStatus.OUT_OF_STOCK;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    /**
     * 상품 목록 조회
     * param : displayStatus
     */
    public List<ProductResponseDto> findProducts() {
        List<Product> products = productRepository.findProductsByStatusIn(List.of(DISPLAY, OUT_OF_STOCK), PageRequest.of(0, 10));
        return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 상품 상세 조회
     * param id
     */
    public Product detail(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }
}
