package com.commerce.user.dto;

import com.commerce.global.common.Address;
import com.commerce.user.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberDto {

    private String userId;
    private String password;
    private String username;
    private String email;
    private String phoneNumber;
    private Address address;

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

}
