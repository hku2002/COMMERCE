package com.commerce.cart.domain;

import com.commerce.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "id", cascade = CascadeType.PERSIST)
    private List<OptionCartMapping> optionCartMappings;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "user_purchase_quantity", nullable = false)
    private int userPurchaseQuantity;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @Builder
    public Cart(Long id, Long userId, Long productId, Long itemId, int userPurchaseQuantity, int itemUsedQuantity) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.itemId = itemId;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.itemUsedQuantity = itemUsedQuantity;
    }

    public void changeCart(Long userId, List<OptionCartMapping> optionCartMappings, Long productId, Long itemId, int userPurchaseQuantity, int itemUsedQuantity, boolean activated, LocalDateTime createdAt) {
        this.userId = userId;
        this.optionCartMappings = optionCartMappings;
        this.productId = productId;
        this.itemId = itemId;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.itemUsedQuantity = itemUsedQuantity;
        this.activated = activated;
        this.createdAt = createdAt;
    }

}
