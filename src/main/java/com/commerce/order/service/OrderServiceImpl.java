package com.commerce.order.service;

import com.commerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl {

    private final OrderRepository orderRepository;

}
