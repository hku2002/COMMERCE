package com.commerce.global.common.dto;

import lombok.Getter;

@Getter
public class ErrorResponseDto {
    private int status;
    private String message;

    public ErrorResponseDto(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
