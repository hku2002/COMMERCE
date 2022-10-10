package com.commerce.product.domain;

import com.commerce.user.constants.Item;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "PRODUCT_DISPLAY_MAPPING")
@NoArgsConstructor(access = PROTECTED)
public class ItemProductMapping {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", updatable = false)
    private Item itemId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    private Product productId;

    @Column(name = "used_stock_quantity", nullable = false)
    private int usedStockQuantity;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ItemProductMapping(Long id, Item itemId, Product productId, int usedQuantity) {
        this.id = id;
        this.itemId = itemId;
        this.productId = productId;
        this.usedStockQuantity = usedQuantity;
        this.createdAt = LocalDateTime.now();
    }
}
