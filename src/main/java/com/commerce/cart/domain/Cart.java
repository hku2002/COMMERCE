package com.commerce.cart.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.product.domain.Item;
import com.commerce.product.domain.Option;
import com.commerce.product.domain.Product;
import com.commerce.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "CART")
@NoArgsConstructor(access = PROTECTED)
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
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

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private List<OptionCartMapping> optionCartMappings;

    @Column(name = "user_purchase_quantity", nullable = false)
    private int userPurchaseQuantity;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @Builder
    public Cart(Long id, Option option, Product product, Item item, Member member, List<OptionCartMapping> optionCartMappings, int userPurchaseQuantity, int itemUsedQuantity) {
        this.id = id;
        this.option = option;
        this.product = product;
        this.item = item;
        this.member = member;
        this.optionCartMappings = optionCartMappings;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.itemUsedQuantity = itemUsedQuantity;
    }

    public void updateActivated(Boolean activated) {
        this.activated = activated;
        this.updatedAt = LocalDateTime.now();
    }

}
