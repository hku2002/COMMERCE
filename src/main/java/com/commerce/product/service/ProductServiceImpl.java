package com.commerce.product.service;

import com.commerce.product.domain.Product;
import com.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    /**
     * 상품 상세 조회
     * param displayId
     */
    public Product detail(Long displayId) {
        return null;
    }

    /**
     * 상품 옵션 목록
     * param displayId, mainProductId
     */
    public List<Product> optionList(Long displayId) {
        return null;
    }

}
