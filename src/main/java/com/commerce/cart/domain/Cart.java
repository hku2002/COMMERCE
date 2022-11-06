package com.commerce.cart.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.product.domain.Item;
import com.commerce.product.domain.Option;
import com.commerce.product.domain.Product;
import com.commerce.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@DynamicUpdate
@Table(name = "CART")
@NoArgsConstructor(access = PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "option_id", updatable = false)
    private Option option;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    private Product product;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", updatable = false)
    private Item item;

    @ManyToOne(fetch = LAZY)
    private Member member;

    @Column(name = "user_purchase_quantity", nullable = false)
    private int userPurchaseQuantity;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @Builder
    public Cart(Long id, Option option, Product product, Item item, Member member, int userPurchaseQuantity, int itemUsedQuantity) {
        this.id = id;
        this.option = option;
        this.product = product;
        this.item = item;
        this.member = member;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.itemUsedQuantity = itemUsedQuantity;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * 활성화여부 변경
     * @param activated 활성화여부
     */
    public void updateActivated(Boolean activated) {
        this.activated = activated;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 사용자 구매 수량, 재고 수량 변경
     * @param userPurchaseQuantity 사용자 구매 수량
     * @param itemUsedQuantity 재고 수량
     */
    public void addQuantity(int userPurchaseQuantity, int itemUsedQuantity) {
        this.userPurchaseQuantity += userPurchaseQuantity;
        this.itemUsedQuantity += userPurchaseQuantity * itemUsedQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    public static void checkCartsEmpty(List<Cart> carts) {
        if (carts.size() < 1) {
            throw new BadRequestException("장바구니에 담긴 상품이 없습니다.");
        }
    }

    /**
     * 장바구니에 담긴 상품이 cartIds 에 포함되어 있는지 확인
     * @param carts 장바구니 목록
     * @param cartIds 장바구니 아이디 목록
     */
    public static void checkContainCartsByIds(List<Cart> carts, List<Long> cartIds) {
        for (Cart cart : carts) {
            if (!cartIds.contains(cart.getId())) {
                throw new BadRequestException("장바구니에 담긴 상품의 정보가 올바르지 않습니다.");
            }
        }
    }

}
