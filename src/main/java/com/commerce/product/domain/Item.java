package com.commerce.product.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.Price;
import com.commerce.global.common.exception.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@DynamicUpdate
@Table(name = "ITEM")
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "img_path", nullable = false, length = 200)
    private String imgPath;

    @Embedded
    private Price price;

    @Column(name = "supply_price", nullable = false)
    private int supplyPrice;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @OneToMany(mappedBy = "item", cascade = PERSIST)
    private List<ItemProductMapping> productProductMappings = new ArrayList<>();

    @Builder
    public Item(long id, String name, String imgPath, Price price, int supplyPrice, int stockQuantity, List<ItemProductMapping> productProductMappings) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.supplyPrice = supplyPrice;
        this.stockQuantity = stockQuantity;
        this.productProductMappings = productProductMappings;
    }

    /**
     * 재고 차감
     * @param stockQuantity 차감할 재고 수량
     */
    public void subtractStock(int stockQuantity) {
        this.stockQuantity -= stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 재고 증가
     * @param stockQuantity 추가할 재고 수량
     */
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * 재고와 장바구니 데이터의 재고 수량을 비교
     * @param cartItemQuantity 장바구니 데이터의 재고 수량
     */
    public void compareStockQuantityWithCartItemQuantity(int cartItemQuantity) {
        if (this.stockQuantity < cartItemQuantity) {
            throw new BadRequestException("재고가 부족합니다.");
        }
    }

}
