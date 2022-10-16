package com.commerce.cart.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.dto.AddCartRequestDto;
import com.commerce.cart.repositiry.CartRepository;
import com.commerce.product.domain.Item;
import com.commerce.product.domain.Product;
import com.commerce.product.repository.ItemRepository;
import com.commerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

    /**
     * 장바구니 추가
     * @param requestDto
     */
    @Transactional
    public void addCart (AddCartRequestDto requestDto) {
        Item item = findItem(requestDto.getProductId());
        Product product = findProduct(requestDto.getProductId());
        cartRepository.save(Cart.builder()
                .userId(1L)
                .productId(product.getId())
                .itemId(item.getId())
                .userPurchaseQuantity(requestDto.getQuantity())
                .itemUsedQuantity(requestDto.getQuantity() * item.getProductProductMapping().getUsedStockQuantity())
                .build());
    }

    /**
     * 아이템 조회 및 validation 여부 확인
     * @param itemId
     */
    private Item findItem (Long itemId) {
        Item item = itemRepository.findByIdWithItemProductMapping(itemId);
        if (ObjectUtils.isEmpty(item)) {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다.");
        }
        return item;
    }

    /**
     * 상품 조회 및 validation 여부 확인
     * @param productId
     */
    private Product findProduct (Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }
}
