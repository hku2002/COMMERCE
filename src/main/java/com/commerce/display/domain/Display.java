package com.commerce.display.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.IEnumType;
import com.commerce.product.domain.ProductDisplayMapping;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.commerce.product.domain.Product.DiscountMethod;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "DISPLAY")
@NoArgsConstructor(access = PROTECTED)
public class Display extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "img_path", nullable = false, length = 200)
    private String imgPath;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Column(name = "discount_rate", nullable = false)
    private int discountRate;

    @Enumerated(STRING)
    @Column(name = "discount_method", length = 30)
    private DiscountMethod discountMethod;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private DisplayStatus status;

    @Enumerated(STRING)
    @Column(name = "composition_type", nullable = false, length = 30)
    private CompositionType compositionType;

    @OneToMany(mappedBy = "productId" , cascade = PERSIST)
    private List<ProductDisplayMapping> productDisplayMappings = new ArrayList<>();

    @Builder
    public Display (long id, String name, String imgPath, int price, int discountPrice, int discountRate, DiscountMethod discountMethod, DisplayStatus status, CompositionType compositionType, List<ProductDisplayMapping> productDisplayMappings) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.discountMethod = discountMethod;
        this.status = status;
        this.compositionType = compositionType;
        this.productDisplayMappings = productDisplayMappings;
    }

    public enum DisplayStatus implements IEnumType {
        STAND_BY    ("STAND_BY"),
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

    public enum CompositionType implements IEnumType {
        SINGLE         ("SINGLE"),
        VARIETY_SET    ("VARIETY_SET"),
        PACKAGE        ("PACKAGE");

        private final String value;
        CompositionType(String value) { this.value = value; }

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
