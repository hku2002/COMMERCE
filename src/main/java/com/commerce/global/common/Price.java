package com.commerce.global.common;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import static javax.persistence.EnumType.STRING;

@Getter
@Embeddable
public class Price {

    @Column(name = "sale_price", nullable = false)
    private int salePrice;

    @Column(name = "discount_price", nullable = false)
    private int discountPrice;

    @Column(name = "discount_rate", nullable = false)
    private int discountRate;

    @Enumerated(STRING)
    @Column(name = "discount_method", nullable = false, length = 30)
    private DiscountMethod discountMethod;

    protected Price() {}

    @Builder
    public Price(int salePrice, int discountPrice, int discountRate, DiscountMethod discountMethod) {
        this.salePrice = salePrice;
        this.discountPrice = discountPrice;
        this.discountRate = discountRate;
        this.discountMethod = discountMethod;
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
