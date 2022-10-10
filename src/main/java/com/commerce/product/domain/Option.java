package com.commerce.product.domain;

import com.commerce.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "OPTION")
@NoArgsConstructor(access = PROTECTED)
public class Option extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "item_product_mapping_id", nullable = false)
    private Long itemProductMappingId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "stage", nullable = false)
    private int stage;

    @Column(name = "parent_id")
    private Long parentId;

    @Builder
    public Option(Long id, Long productId, Long itemId, Long itemProductMappingId, String name, int stage, Long parentId) {
        this.id = id;
        this.productId = productId;
        this.itemId = itemId;
        this.itemProductMappingId = itemProductMappingId;
        this.name = name;
        this.stage = stage;
        this.parentId = parentId;
    }

}
