package com.commerce.product.service;

import com.commerce.global.common.Price;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import com.commerce.product.domain.Product;
import com.commerce.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.commerce.global.common.Price.DiscountMethod.PRICE;
import static com.commerce.product.domain.Product.CompositionType.SINGLE;
import static com.commerce.product.domain.Product.DisplayStatus.DISPLAY;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

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
        for (int i = 0; i < 3; i++) {
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

        // when
        PagingCommonRequestDto pagingCommonRequestDto = new PagingCommonRequestDto();
        pagingCommonRequestDto.setLimit(0);
        pagingCommonRequestDto.setOffset(3);

        // then
        assertAll(
                () -> assertThat(productServiceImpl.findProducts(pagingCommonRequestDto).get(0).getId(), is(1L)),
                () -> assertThat(productServiceImpl.findProducts(pagingCommonRequestDto).get(1).getId(), is(2L)),
                () -> assertThat(productServiceImpl.findProducts(pagingCommonRequestDto).get(2).getId(), is(3L))
        );

    }

}
