package com.shop.delivery.domain;

import com.shop.global.common.Address;
import com.shop.global.common.BaseEntity;
import com.shop.global.common.IEnumType;
import com.shop.user.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "DELIVERY")
public class Delivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", insertable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", updatable = false)
    private Member member;

    @Embedded
    private Address address;

    public Delivery (Long id, Member member, Address address) {
        this.id = id;
        this.member = member;
        this.address = address;
    }

    public enum DeliveryStatus implements IEnumType {
        WAIT        ("WAIT"),
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
