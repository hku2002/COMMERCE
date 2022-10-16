package com.commerce.product.domain;

import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.IEnumType;
import com.commerce.global.common.Price;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor(access = PROTECTED)
public class Product extends BaseEntity {

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

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private DisplayStatus status;

    @Enumerated(STRING)
    @Column(name = "composition_type", nullable = false, length = 30)
    private CompositionType compositionType;

    @Column(name = "main_item_id", nullable = false)
    private Long mainItemId;

    @OneToMany(mappedBy = "productId" , cascade = PERSIST)
    private List<ItemProductMapping> productDisplayMappings = new ArrayList<>();

    @Builder
    public void Product(long id, String name, String imgPath, Price price, DisplayStatus status, CompositionType compositionType, Long mainItemId) {
        this.id = id;
        this.name = name;
        this.imgPath = imgPath;
        this.price = price;
        this.status = status;
        this.compositionType = compositionType;
        this.mainItemId = mainItemId;
    }

    public enum DisplayStatus implements IEnumType {
        STAND_BY    ("STAND_BY"),
        DISPLAY     ("DISPLAY"),
        OUT_OF_STOCK("OUT_OF_STOCK"),
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
