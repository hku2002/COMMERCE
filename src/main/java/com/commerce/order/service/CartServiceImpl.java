package com.commerce.order.service;

import com.commerce.order.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl {

    private final CartRepository cartRepository;

}
