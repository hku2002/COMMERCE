package com.commerce.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private Long memberId;
    private String username;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, Long memberId, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberId = memberId;
        this.username = username;
    }
}
