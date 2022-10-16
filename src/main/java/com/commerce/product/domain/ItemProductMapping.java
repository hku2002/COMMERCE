package com.commerce.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "ITEM_PRODUCT_MAPPING")
@NoArgsConstructor(access = PROTECTED)
public class ItemProductMapping {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id", updatable = false)
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    private Product product;

    @Column(name = "item_used_quantity", nullable = false)
    private int itemUsedQuantity;

    @Column(name = "activated", nullable = false)
    private boolean activated;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ItemProductMapping(Long id, Item item, Product product, int itemUsedQuantity, boolean activated) {
        this.id = id;
        this.item = item;
        this.product = product;
        this.itemUsedQuantity = itemUsedQuantity;
        this.activated = activated;
        this.createdAt = LocalDateTime.now();
    }
}
