package com.shop.user.domain;

import com.shop.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "MEMBER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true, length = 50)
    private String userId;

    @Column(name = "password", nullable = false, length = 64)
    private String password;

    @Column(name = "name", nullable = false, length = 50)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "phone", nullable = false, length = 11)
    private String phoneNumber;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "address_detail", length = 100)
    private String addressDetail;

    @Column(name = "zip_code", length = 5)
    private String zipCode;

    @Builder
    public Member(String userId, String password, String username, String email, String phoneNumber, String address, String addressDetail, String zipCode) {
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.zipCode = zipCode;
    }

}
