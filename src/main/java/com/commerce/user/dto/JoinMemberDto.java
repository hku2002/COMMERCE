package com.commerce.user.dto;

import com.commerce.global.common.Address;
import com.commerce.global.common.util.CryptoUtils;
import com.commerce.user.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class JoinMemberDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름를 입력해주세요.")
    private String username;
    private String email;
    private String phoneNumber;
    private Address address;

    public void encryptPassword(String plainPassword) {
        this.password = CryptoUtils.passwordEncoder(plainPassword);
    }

    public Member toEntity() {
        return Member.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .email(email)
                .phoneNumber(phoneNumber)
                .address(address)
                .activated(true)
                .createdAt(LocalDateTime.now())
                .build();
    }

}
