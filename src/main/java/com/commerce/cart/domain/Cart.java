package com.commerce.cart.domain;

import com.commerce.global.common.BaseEntity;
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

    public void updateActivated(Boolean activated) {
        this.activated = activated;
        this.updatedAt = LocalDateTime.now();
    }

    public void addQuantity(int userPurchaseQuantity, int itemUsedQuantity) {
        this.userPurchaseQuantity += userPurchaseQuantity;
        this.itemUsedQuantity += userPurchaseQuantity * itemUsedQuantity;
        this.updatedAt = LocalDateTime.now();
    }

}
