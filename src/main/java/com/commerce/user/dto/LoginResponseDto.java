package com.commerce.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private String userId;
    private String username;

    @Builder
    public LoginResponseDto(String accessToken, String refreshToken, String userId, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.username = username;
    }
}
