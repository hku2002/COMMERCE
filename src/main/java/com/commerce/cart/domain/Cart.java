package com.commerce.cart.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.Price;
import com.commerce.product.domain.Option;
import com.commerce.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private List<OptionCartMapping> optionCartMappings;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "user_purchase_quantity", nullable = false)
    private int userPurchaseQuantity;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @Embedded
    private Price price;

    @Builder
    public Cart(Long id, Option option, Long userId, Product product, Long itemId, int userPurchaseQuantity, int itemUsedQuantity, Price price) {
        this.id = id;
        this.option = option;
        this.product = product;
        this.userId = userId;
        this.itemId = itemId;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.itemUsedQuantity = itemUsedQuantity;
        this.price = price;
    }

    public void updateActivated(Boolean activated) {
        this.activated = activated;
    }

}
