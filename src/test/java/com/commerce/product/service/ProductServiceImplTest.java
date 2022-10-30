package com.commerce.product.service;

import com.commerce.global.common.Price;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import com.commerce.product.domain.Product;
import com.commerce.product.dto.ProductResponseDto;
import com.commerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.global.common.Price.DiscountMethod.PRICE;
import static com.commerce.product.domain.Product.CompositionType.SINGLE;
import static com.commerce.product.domain.Product.DisplayStatus.DISPLAY;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ProductServiceImplTest {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductServiceImpl productServiceImpl;

    @Test
    @Description("상품조회")
    void findProductsTest() {
        // given
        Price price = Price.builder()
                .defaultPrice(10000)
                .discountPrice(1000)
                .salePrice(9000)
                .discountRate(0)
                .discountMethod(PRICE)
                .build();

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            products.add(Product.builder()
                    .imgPath("/img/test" + i)
                    .price(price)
                    .compositionType(SINGLE)
                    .name("test 상품" + i)
                    .status(DISPLAY)
                    .mainItemId(1L)
                    .build());
        }

        productRepository.saveAll(products);
        System.out.println("products = " + products);
        List<ProductResponseDto> results = products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
        System.out.println("results1 = " + results);

        // when
        PagingCommonRequestDto pagingCommonRequestDto = new PagingCommonRequestDto();
        pagingCommonRequestDto.setLimit(0);
        pagingCommonRequestDto.setOffset(3);
        System.out.println("results2 = " + productServiceImpl.findProducts(pagingCommonRequestDto));

        // then
        assertEquals(productServiceImpl.findProducts(pagingCommonRequestDto).get(0).getId(), results.get(0).getId());
    }

}
