package com.commerce.global.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {

    private Long memberId;
    private String userId;

    @Builder
    public TokenDto(Long memberId, String userId) {
        this.memberId = memberId;
        this.userId = userId;
    }

}
