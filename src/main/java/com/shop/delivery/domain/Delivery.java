package com.shop.delivery.domain;

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

    @Column(name = "address", length = 100, nullable = false)
    private String address;

    @Column(name = "address_detail", length = 100)
    private String addressDetail;

    @Column(name = "zip_code", length = 5, nullable = false)
    private String zipCode;

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
