package com.shop.product.domain;

import com.shop.global.common.BaseEntity;
import com.shop.global.common.IEnumType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "img_path", nullable = false, length = 200)
    private String imgPath;

    @Column(name = "sale_price", nullable = false)
    private int salePrice;

    @Column(name = "discount_price", nullable = false, columnDefinition = "int default 0")
    private int discountPrice;

    @Column(name = "discount_rate", nullable = false, columnDefinition = "int default 0")
    private int discountRate;

    @Enumerated(STRING)
    @Column(name = "discount_method", nullable = false, length = 30, columnDefinition = "varchar(10) default 'NO_DISCOUNT'")
    private DiscountMethod discountMethod;

    @Column(name = "purchase_price", nullable = false)
    private int purchasePrice;

    @Column(name = "stock", nullable = false)
    private int stock;

    @OneToMany(mappedBy = "productId" , cascade = PERSIST)
    private List<ProductDisplayMapping> productDisplayMappings = new ArrayList<>();

    @Builder
    public Product (long id, String name, String imgPath, int salePrice, int discountPrice, int discountRate, DiscountMethod discountMethod, int purchasePrice, int stock, List<ProductDisplayMapping> productDisplayMappings) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.salePrice = salePrice;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.discountMethod = discountMethod;
        this.purchasePrice = purchasePrice;
        this.stock = stock;
        this.productDisplayMappings = productDisplayMappings;
    }

    public enum DiscountMethod implements IEnumType {
        RATE        ("RATE"),
        PRICE       ("PRICE"),
        NO_DISCOUNT ("NO_DISCOUNT");

        private final String value;
        DiscountMethod(String value) { this.value = value; }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getName() {
            return value;
        }
    }

}
