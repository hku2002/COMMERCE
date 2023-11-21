package com.commerce.product.service;

import com.commerce.global.common.exception.BadRequestException;
import com.commerce.product.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    @Test
    @DisplayName("상품상세 조회 시 상품 데이터가 없을 경우 예외를 던진다.")
    void findProductEmptyProductThrow() {
        // given
        given(productRepository.findByIdAndActivated(anyLong(), anyBoolean())).willReturn(null);

        // then
        assertThatThrownBy(() -> productServiceImpl.findProduct(1L)).isInstanceOf(BadRequestException.class);
    }

}
