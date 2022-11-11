package com.commerce.order.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.repository.CartRepository;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.global.common.token.JwtTokenManager;
import com.commerce.order.repository.OrderRepository;
import com.commerce.user.domain.Member;
import com.commerce.user.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @InjectMocks
    private OrderServiceImpl orderServiceImpl;

    @Test
    @DisplayName("주문 생성 시 회원 정보가 없으면 예외로 던진다.")
    void addOrderEmptyMemberThrow() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(null);
        given(jwtTokenManager.getUserIdByToken()).willReturn("test");

        // when
        List<Long> cartIds = List.of(1L, 2L, 3L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("주문 생성 시 장바구니 정보가 없으면 예외로 던진다.")
    void addOrderEmptyCartThrow() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        List<Cart> cart = new ArrayList<>();
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(cart);

        // when
        List<Long> cartIds = List.of(1L, 2L, 3L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds))
                .isInstanceOf(BadRequestException.class);
    }

}