package com.commerce.order.service;

import com.commerce.order.dto.OrderResponseDto;
import com.commerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

    /**
     * 주문 목록 조회
     * @param userId
     */
    public List<OrderResponseDto> findOrders(Long userId, int limit, int offset) {
        return orderRepository.findAllWithOrderProducts(userId, PageRequest.of(limit, offset))
                .stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }

}
