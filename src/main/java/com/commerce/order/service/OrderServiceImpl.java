package com.commerce.order.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.repositiry.CartRepository;
import com.commerce.delivery.domain.Delivery;
import com.commerce.delivery.repository.DeliveryRepository;
import com.commerce.order.domain.Order;
import com.commerce.order.domain.OrderItem;
import com.commerce.order.dto.OrderResponseDto;
import com.commerce.order.repository.OrderItemRepository;
import com.commerce.order.repository.OrderRepository;
import com.commerce.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.commerce.delivery.domain.Delivery.DeliveryStatus.STAND_BY;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderServiceImpl {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final DeliveryRepository deliveryRepository;

    /**
     * 주문 목록 조회
     * @param userId
     */
    public List<OrderResponseDto> findOrders(Long userId, int limit, int offset) {
        return orderRepository.findWithOrderProductsAndDeliveryByUserId(userId, PageRequest.of(limit, offset))
                .stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 주문 추가 (주문 완료)
     */
    @Transactional
    public void addOrder(List<Long> cartIds) {
        List<Cart> carts = findCarts(cartIds);
        Member member = Member.builder().id(1L).build();

        int totalPrice = 0;
        for (Cart cart : carts) {
            totalPrice += cart.getItem().getPrice().getSalePrice();
        }

        Order order = orderRepository.save(
                Order.builder()
                .member(member)
                .name("주문")
                .totalPrice(totalPrice)
                .build());

        List<OrderItem> orderItems = new ArrayList<>();
        for (Cart cart : carts) {
            orderItems.add(OrderItem.builder()
                    .order(order)
                    .itemId(cart.getItem().getId())
                    .price(cart.getItem().getPrice())
                    .supplyPrice(cart.getItem().getSupplyPrice())
                    .userPurchaseQuantity(cart.getUserPurchaseQuantity())
                    .itemUsedQuantity(cart.getItemUsedQuantity())
                    .build());
            cart.updateActivated(false);
        }
        orderItemRepository.saveAll(orderItems);
        deliveryRepository.save(Delivery.builder()
                .member(member)
                .order(order)
                .address(member.getAddress())
                .status(STAND_BY)
                .build());
    }

    /**
     * 주문 취소
     */
    public void cancelOrder() {
    }

    /**
     * 장바구니 목록 조회
     * @param cartIds
     */
    private List<Cart> findCarts(List<Long> cartIds) {
        return cartRepository.findWithOptionAndItem(cartIds);
    }

}
