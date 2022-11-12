package com.commerce.order.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.repository.CartRepository;
import com.commerce.delivery.repository.DeliveryRepository;
import com.commerce.global.common.Price;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.global.common.token.JwtTokenManager;
import com.commerce.order.domain.Order;
import com.commerce.order.domain.OrderItem;
import com.commerce.order.repository.OrderItemRepository;
import com.commerce.order.repository.OrderRepository;
import com.commerce.product.domain.Item;
import com.commerce.product.domain.Product;
import com.commerce.product.repository.ItemRepository;
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
import java.util.Optional;

import static com.commerce.order.domain.Order.OrderStatus.COMPLETED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

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

    @Test
    @DisplayName("주문 생성 시 장바구니에 담긴 상품이 cartIds 에 포함되어 있지 않으면 예외를 던진다.")
    void addOrderCheckContainCartsByIdsThrow() {
        // given
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder().id(1L).build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(carts);

        // when
        List<Long> cartIds = List.of(2L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("주문 생성 시 장바구니 id에 연관된 재고가 없으면 예외를 던진다.")
    void addOrderCheckItemExistByCartIdThrow() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .item(Item.builder().id(2L).build())
                .build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(carts);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(items);
        given(itemRepository.findById(2L)).willThrow(BadRequestException.class);

        // when
        List<Long> cartIds = List.of(1L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("주문 생성 시 주문 테이블 저장 메소드를 한번 호출하였는지 확인")
    void addOrderCheckOrderSave() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .product(Product.builder().id(1L).name("테스트 상품").build())
                .item(Item.builder().id(1L).price(Price.builder().salePrice(1000).build()).build())
                .build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(carts);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(items);
        given(itemRepository.findById(1L)).willReturn(Optional.of(Item.builder().id(1L).build()));

        // when
        List<Long> cartIds = List.of(1L);
        orderServiceImpl.addOrder(cartIds);

        // then
        verify(orderRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("주문 생성 시 주문 아이템 테이블 저장 메소드를 한번 호출하였는지 확인")
    void addOrderCheckOrderItemsSave() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .product(Product.builder().id(1L).name("테스트 상품").build())
                .item(Item.builder().id(1L).price(Price.builder().salePrice(1000).build()).build())
                .build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(carts);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(items);
        given(itemRepository.findById(1L)).willReturn(Optional.of(Item.builder().id(1L).build()));

        // when
        List<Long> cartIds = List.of(1L);
        orderServiceImpl.addOrder(cartIds);

        // then
        verify(orderItemRepository, times(1)).saveAll(any());
    }

    @Test
    @DisplayName("주문 생성 시 장바구니 id에 연관된 재고가 주문할 수량보다 부족할 경우 예외를 던진다.")
    void addOrderItemEmptyCheck() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .itemUsedQuantity(10)
                .item(Item.builder().id(2L).build())
                .build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(carts);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(items);
        given(itemRepository.findById(2L)).willReturn(Optional.of(Item.builder().id(2L).stockQuantity(5).build()));

        // when
        List<Long> cartIds = List.of(1L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("주문 완료 시 이미 주문이 완료된 경우 예외를 던진다.")
    void completeOrderAlreadyCompleteThrow() {
        // given
        Order order = Order.builder().id(1L).build();
        order.updateOrderStatus(COMPLETED);
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(orderRepository.findByIdAndActivated(anyLong(), anyBoolean())).willReturn(order);

        // when
        Long orderId = 1L;

        // then
        assertThatThrownBy(() -> orderServiceImpl.completeOrder(orderId))
                .isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("주문 완료 시 주문 완료 처리 메소드를 한번 호출하였는지 확인")
    void completeOrderUpdateOrderStatusMethodCallOnceCheck() {
        // given
        Order order = mock(Order.class);
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(orderRepository.findByIdAndActivated(anyLong(), anyBoolean())).willReturn(order);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(List.of(Item.builder().build()));

        // when
        orderServiceImpl.completeOrder(1L);

        // then
        verify(order, times(1)).updateOrderStatus(COMPLETED);
    }

    @Test
    @DisplayName("주문 완료 시 남은재고 수량비교, 재고차감 메소드를 한번 이상 호출한다.")
    void completeOrderCheckStockQuantityAndSubtractStockCalls() {
        // given
        Order order = Order.builder().build();
        Item item = mock(Item.class);
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).stockQuantity(10).build());

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().id(1L).itemId(1L).itemUsedQuantity(5).build());

        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        given(orderRepository.findByIdAndActivated(anyLong(), anyBoolean())).willReturn(order);
        given(orderItemRepository.findAllByOrderIdAndActivated(anyLong(), anyBoolean())).willReturn(orderItems);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(items);
        given(itemRepository.findById(1L)).willReturn(Optional.ofNullable(item));

        // when
        orderServiceImpl.completeOrder(1L);

        // then
        verify(item, atLeastOnce()).compareStockQuantityWithItemQuantity(anyInt());
        verify(item, atLeastOnce()).subtractStock(anyInt());

    }

}
