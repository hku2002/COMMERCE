package com.shop.product.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.display.domain.Display;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "PRODUCT_DISPLAY_MAPPING")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductDisplayMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", updatable = false)
    private Product productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "display_id", updatable = false)
    private Display displayId;

    @Column(name = "used_stock", nullable = false, columnDefinition = "int default 1")
    private int usedStock;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public ProductDisplayMapping (Long id, Product productId, Display displayId, int usedStock) {
        this.id = id;
        this.productId = productId;
        this.displayId = displayId;
        this.usedStock = usedStock;
        this.createdAt = LocalDateTime.now();
    }
}
