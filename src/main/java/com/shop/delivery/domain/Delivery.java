package com.shop.delivery.domain;

import com.shop.global.common.Address;
import com.shop.global.common.BaseEntity;
import com.shop.global.common.IEnumType;
import com.shop.user.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
@Table(name = "DELIVERY")
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member member;

    @Embedded
    private Address address;

    @Column(name = "status", nullable = false, length = 30)
    private DeliveryStatus status;

    public Delivery (Long id, Member member, Address address, DeliveryStatus status) {
        this.id = id;
        this.member = member;
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

}
