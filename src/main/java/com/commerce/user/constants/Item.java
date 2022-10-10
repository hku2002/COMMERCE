package com.commerce.user.constants;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.Price;
import com.commerce.product.domain.ItemProductMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "ITEM")
@NoArgsConstructor(access = PROTECTED)
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
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

    @OneToMany(mappedBy = "itemId", cascade = PERSIST)
    private List<ItemProductMapping> productDisplayMappings = new ArrayList<>();

    @Builder
    public Item(long id, String name, String imgPath, Price price, int supplyPrice, int stockQuantity, List<ItemProductMapping> productDisplayMappings) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.supplyPrice = supplyPrice;
        this.stockQuantity = stockQuantity;
        this.productDisplayMappings = productDisplayMappings;
    }

}
