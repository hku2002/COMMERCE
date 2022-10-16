package com.commerce.cart.service;

import com.commerce.cart.domain.Cart;
import com.commerce.cart.dto.AddCartRequestDto;
import com.commerce.cart.dto.CartResponseDto;
import com.commerce.cart.repositiry.CartRepository;
import com.commerce.global.common.dto.PagingCommonRequestDto;
import com.commerce.product.domain.Item;
import com.commerce.product.domain.Option;
import com.commerce.product.domain.Product;
import com.commerce.product.repository.ItemRepository;
import com.commerce.product.repository.OptionRepository;
import com.commerce.product.repository.ProductRepository;
import com.commerce.user.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartServiceImpl {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;
    private final OptionRepository optionRepository;

    /**
     * 장바구니 목록 조회
     * @param requestDto
     */
    public List<CartResponseDto> findCarts(PagingCommonRequestDto requestDto) {
        return cartRepository.findWithOptionAndProductAndItemByMemberId(1L, PageRequest.of(requestDto.getLimit(), requestDto.getOffset()))
                .stream().map(CartResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 장바구니 추가
     * @param requestDto
     */
    @Transactional
    public void addCart(AddCartRequestDto requestDto) {
        Cart cart = checkCart(requestDto.getItemId(), requestDto.getProductId());
        if (!ObjectUtils.isEmpty(cart)) {
            cart.addQuantity(requestDto.getQuantity());
            return;
        }

        Item item = findItem(requestDto.getItemId());
        Product product = findProduct(requestDto.getProductId());
        Option option = findOption(item.getId(), product.getId());
        cartRepository.save(Cart.builder()
                .member(Member.builder().id(1L).build())
                .product(product)
                .item(item)
                .option(option)
                .userPurchaseQuantity(requestDto.getQuantity())
                .itemUsedQuantity(requestDto.getQuantity() * item.getProductProductMapping().getUsedStockQuantity())
                .build());
    }

    /**
     * 장바구니 삭제 (update)
     * @param cartId
     */
    @Transactional
    public void deleteCart(Long cartId) {
        Cart cart = findCart(cartId);
        cart.updateActivated(false);
    }

    /**
     * 아이템 조회 및 validation 여부 확인
     * @param itemId
     */
    private Item findItem(Long itemId) {
        Item item = itemRepository.findTop1ByIdAndActivated(itemId, true);
        if (ObjectUtils.isEmpty(item)) {
            throw new IllegalArgumentException("해당 아이템이 존재하지 않습니다.");
        }
        return item;
    }

    /**
     * 상품 조회 및 validation 여부 확인
     * @param productId
     */
    private Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }

    /**
     * 장바구니 조회 및 validation 여부 확인
     * @param cartId
     */
    private Cart findCart(Long cartId) {
        Cart cart = cartRepository.findByIdAndMemberId(cartId, 1L);
        if (ObjectUtils.isEmpty(cart)) {
            throw new IllegalArgumentException("해당 장바구니가 존재하지 않습니다.");
        }
        return cart;
    }

    /**
     * 장바구니에 동일 정보가 존재하는지 확인
     * @param itemId
     * @param productId
     */
    private Cart checkCart(Long itemId, Long productId) {
        return cartRepository.findTop1ByItemIdAndProductIdAndActivated(itemId, productId, true);
    }

    /**
     * 옵션 조회
     * @param itemId
     * @param productId
     */
    private Option findOption(Long itemId, Long productId) {
        Option option = optionRepository.findTop1ByItemIdAndProductIdAndActivated(itemId, productId, true);
        if (ObjectUtils.isEmpty(option)) {
            throw new IllegalArgumentException("해당 옵션이 존재하지 않습니다.");
        }
        return option;
    }
}
