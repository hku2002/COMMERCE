package com.commerce.order.domain;

import com.commerce.global.common.Price;
import com.commerce.product.domain.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "ORDER_PRODUCT")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", updatable = false)
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false)
    private Product product;

    @Embedded
    private Price price;

    @Column(name = "supply_price", nullable = false)
    private int supplyPrice;

    @Column(name = "user_purchase_quantity", nullable = false)
    private int userPurchaseQuantity;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public OrderProduct(long id, Order order, Product product, Price price, int supplyPrice, int userPurchaseQuantity, boolean activated, LocalDateTime createdAt) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.price = price;
        this.supplyPrice = supplyPrice;
        this.userPurchaseQuantity = userPurchaseQuantity;
        this.activated = activated;
        this.createdAt = createdAt;
    }

}
