package com.commerce.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.commerce.display.domain.Display;
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
public class ProductDisplayMapping {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    private Product productId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "display_id", updatable = false)
    private Display displayId;

    @Column(name = "used_quantity", nullable = false)
    private int usedQuantity;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ProductDisplayMapping (Long id, Product productId, Display displayId, int usedQuantity) {
        this.id = id;
        this.productId = productId;
        this.displayId = displayId;
        this.usedQuantity = usedQuantity;
        this.createdAt = LocalDateTime.now();
    }
}
