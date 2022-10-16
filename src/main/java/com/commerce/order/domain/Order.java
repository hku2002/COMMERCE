package com.commerce.order.domain;

import com.commerce.delivery.domain.Delivery;
import com.commerce.global.common.IEnumType;
import com.commerce.user.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member member;

    @OneToOne(mappedBy = "order", cascade = PERSIST)
    private Delivery delivery;

    @OneToMany(mappedBy = "order", cascade = PERSIST)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private OrderStatus status;

    @Column(name = "total_price", nullable = false)
    private int totalPrice;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime orderDateTime;

    @Builder
    public Order (Long id, Member member, Delivery delivery, List<OrderProduct> orderProducts, String name, int totalPrice) {
        this.id = id;
        this.member = member;
        this.delivery = delivery;
        this.orderProducts = orderProducts;
        this.name = name;
        this.status = OrderStatus.COMPLETED;
        this.totalPrice = totalPrice;
        this.orderDateTime = LocalDateTime.now();
    }

    public enum OrderStatus implements IEnumType {
        COMPLETED   ("COMPLETED"),
        CANCELED    ("CANCELED");

        private final String value;
        OrderStatus(String value) { this.value = value; }

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
