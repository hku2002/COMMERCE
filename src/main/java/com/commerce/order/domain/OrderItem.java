package com.commerce.order.domain;

import com.commerce.global.common.Price;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.GenerationType.AUTO;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "ORDER_ITEM")
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_product_id", referencedColumnName = "id", updatable = false)
    private OrderProduct orderProduct;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Embedded
    private Price price;

    @Column(name = "supply_price", nullable = false)
    private int supplyPrice;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public OrderItem(Long id, OrderProduct orderProduct, Long itemId, Price price, int supplyPrice, int itemUsedQuantity, boolean activated, LocalDateTime createdAt) {
        this.id = id;
        this.orderProduct = orderProduct;
        this.itemId = itemId;
        this.price = price;
        this.supplyPrice = supplyPrice;
        this.itemUsedQuantity = itemUsedQuantity;
        this.activated = activated;
        this.createdAt = createdAt;
    }
}
