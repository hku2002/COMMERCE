package com.shop.display.domain;

import com.shop.global.common.BaseEntity;
import com.shop.global.common.IEnumType;
import com.shop.product.domain.Product;
import com.shop.product.domain.ProductDisplayMapping;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "DISPLAY")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Display extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "img_path", nullable = false, length = 200)
    private String imgPath;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount_price", nullable = false, columnDefinition = "int default 0")
    private int discountPrice;

    @Column(name = "discount_rate", nullable = false, columnDefinition = "int default 0")
    private int discountRate;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_method", length = 10)
    private Product.DiscountMethod discountMethod;

    @Column(name = "status", nullable = false)
    private DisplayStatus status;

    @OneToMany(mappedBy = "productId" , cascade = CascadeType.PERSIST)
    private List<ProductDisplayMapping> productDisplayMappings = new ArrayList<>();

    @Builder
    public Display(long id, String name, String imgPath, int price, int discountPrice, int discountRate, Product.DiscountMethod discountMethod, DisplayStatus status, List<ProductDisplayMapping> productDisplayMappings) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.discountMethod = discountMethod;
        this.status = status;
        this.productDisplayMappings = productDisplayMappings;
    }

    public enum DisplayStatus implements IEnumType {
        WAIT        ("WAIT"),
        DISPLAY     ("DISPLAY"),
        SOLD_OUT    ("SOLD_OUT"),
        END         ("END");

        private final String value;
        DisplayStatus(String value) { this.value = value; }

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
