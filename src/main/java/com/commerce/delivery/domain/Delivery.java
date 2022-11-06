package com.commerce.delivery.domain;

import com.commerce.global.common.Address;
import com.commerce.global.common.BaseEntity;
import com.commerce.global.common.IEnumType;
import com.commerce.order.domain.Order;
import com.commerce.user.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "DELIVERY")
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member member;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", updatable = false)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 30)
    private DeliveryStatus status;

    @Builder
    public Delivery (Long id, Member member, Order order, Address address, DeliveryStatus status) {
        this.id = id;
        this.member = member;
        this.order = order;
        this.address = address;
        this.status = status;
    }

    public enum DeliveryStatus implements IEnumType {
        STAND_BY    ("STAND_BY"),
        IN_DELIVERY ("IN_DELIVERY"),
        DELIVERED   ("DELIVERED"),
        CANCELED    ("CANCELED");

        private final String value;
        DeliveryStatus(String value) { this.value = value; }

        @Override
        public String getCode() {
            return name();
        }

        @Override
        public String getName() {
            return value;
        }
    }

    /**
     * 배송 상태 변경
     * @param status 배송상태값
     */
    public void updateDeliveryStatus(DeliveryStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

}
