package com.commerce.order.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.repository.CartRepository;
import com.commerce.delivery.domain.Delivery;
import com.commerce.delivery.domain.Delivery.DeliveryStatus;
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

import static com.commerce.delivery.domain.Delivery.DeliveryStatus.IN_DELIVERY;
import static com.commerce.delivery.domain.Delivery.DeliveryStatus.STAND_BY;
import static com.commerce.order.domain.Order.OrderStatus.CANCELED;
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
    @DisplayName("?????? ?????? ??? ?????? ????????? ????????? ????????? ?????????.")
    void addOrderMemberNotFoundThrow() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(null);
        given(jwtTokenManager.getUserIdByToken()).willReturn("test");

        // when
        List<Long> cartIds = List.of(1L, 2L, 3L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ???????????? ????????? ????????? ????????? ?????????.")
    void addOrderCartNotFoundThrow() {
        // given
        given(memberRepository.findByUserIdAndActivated(anyString(), anyBoolean())).willReturn(Member.builder().build());
        given(jwtTokenManager.getUserIdByToken()).willReturn("testId");
        List<Cart> cart = new ArrayList<>();
        given(cartRepository.findCartsByCartIdsAndMemberId(anyList(), any())).willReturn(cart);

        // when
        List<Long> cartIds = List.of(1L, 2L, 3L);

        // then
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ??????????????? ?????? ????????? cartIds ??? ???????????? ?????? ????????? ????????? ?????????.")
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
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ???????????? id??? ????????? ????????? ????????? ????????? ?????????.")
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
        assertThatThrownBy(() -> orderServiceImpl.addOrder(cartIds)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????? ????????? ?????? ???????????? ?????? ?????????????????? ??????")
    void addOrderCheckOrderSave() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .product(Product.builder().id(1L).name("????????? ??????").build())
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
    @DisplayName("?????? ?????? ??? ?????? ????????? ????????? ?????? ???????????? ?????? ?????????????????? ??????")
    void addOrderCheckOrderItemsSave() {
        // given
        List<Item> items = new ArrayList<>();
        items.add(Item.builder().id(1L).build());
        List<Cart> carts = new ArrayList<>();
        carts.add(Cart.builder()
                .id(1L)
                .product(Product.builder().id(1L).name("????????? ??????").build())
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
    @DisplayName("?????? ?????? ??? ???????????? id??? ????????? ????????? ????????? ???????????? ????????? ?????? ????????? ?????????.")
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
    @DisplayName("?????? ?????? ??? ?????? ????????? ????????? ?????? ????????? ?????????.")
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
    @DisplayName("?????? ?????? ??? ?????? ?????? ?????? ???????????? ?????? ?????????????????? ??????")
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
    @DisplayName("?????? ?????? ??? ???????????? ????????????, ???????????? ???????????? ?????? ?????? ????????????.")
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

    @Test
    @DisplayName("?????? ?????? ??? ?????? ???????????? ???????????? ?????? ?????? ????????? ?????????.")
    void cancelOrderDataNotFoundThrow() {
        // given
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(null);

        // when
        Long orderId = 1L;

        // then
        assertThatThrownBy(() -> orderServiceImpl.cancelOrder(orderId)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????? ????????? ????????? ?????? ????????? ?????????.")
    void cancelOrderAlreadyCanceledThrow() {
        // given
        Order order = Order.builder().id(1L).build();
        order.updateOrderStatus(CANCELED);
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(order);

        // when
        Long orderId = 1L;

        // then
        assertThatThrownBy(() -> orderServiceImpl.cancelOrder(orderId)).isInstanceOf(BadRequestException.class);
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????? ????????? ???????????? ?????? ?????? ????????? ?????????.")
    void cancelOrderNotCancelableThrow() {
        // given
        Delivery delivery = Delivery.builder().id(1L).status(IN_DELIVERY).build();
        Order order = Order.builder().id(1L).delivery(delivery).build();
        order.updateOrderStatus(COMPLETED);
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(order);

        // when
        Long orderId = 1L;

        // then
        assertThatThrownBy(() -> orderServiceImpl.cancelOrder(orderId)).isInstanceOf(BadRequestException.class)
                .hasMessage("????????? ???????????? ????????? ?????? ????????? ???????????????.");
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????????????????? ???????????? ?????? ?????????????????? ??????")
    void cancelOrderUpdateOrderStatusMethodCallOnceCheck() {
        // given
        Delivery delivery = Delivery.builder().id(1L).status(STAND_BY).build();
        Order order = spy(Order.builder().id(1L).delivery(delivery).build());
        order.updateOrderStatus(COMPLETED);
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(order);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(List.of(Item.builder().build()));

        // when
        orderServiceImpl.cancelOrder(1L);

        // then
        verify(order, times(1)).updateOrderStatus(CANCELED);
    }

    @Test
    @DisplayName("?????? ?????? ??? ?????????????????? ???????????? ?????? ?????????????????? ??????")
    void cancelOrderUpdateDeliveryStatusMethodCallOnceCheck() {
        // given
        Delivery delivery = spy(Delivery.builder().id(1L).status(STAND_BY).build());
        Order order = spy(Order.builder().id(1L).delivery(delivery).build());
        order.updateOrderStatus(COMPLETED);
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(order);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(List.of(Item.builder().build()));

        // when
        orderServiceImpl.cancelOrder(1L);

        // then
        verify(delivery, times(1)).updateDeliveryStatus(DeliveryStatus.CANCELED);
    }

    @Test
    @DisplayName("?????? ?????? ??? ??????????????? ????????? ???????????? ???????????? ?????? ?????? ?????????????????? ??????")
    void cancelOrderAddStockMethodCallsCheck() {
        // given
        Delivery delivery = Delivery.builder().id(1L).status(STAND_BY).build();
        Order order = Order.builder().id(1L).delivery(delivery).build();
        order.updateOrderStatus(COMPLETED);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(OrderItem.builder().id(1L).itemId(1L).itemUsedQuantity(5).build());
        Item item = spy(Item.builder().id(1L).stockQuantity(10).build());
        given(orderRepository.findWithDeliveryByOrderId(anyLong())).willReturn(order);
        given(itemRepository.findAllByIdInAndActivated(anyList(), anyBoolean())).willReturn(List.of(Item.builder().build()));
        given(orderItemRepository.findAllByOrderIdAndActivated(anyLong(), anyBoolean())).willReturn(orderItems);
        given(itemRepository.findById(1L)).willReturn(Optional.of(item));

        // when
        orderServiceImpl.cancelOrder(1L);

        // then
        verify(item, atLeastOnce()).addStock(anyInt());
    }

}
