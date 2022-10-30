package com.commerce.global.common.exception;

import lombok.Getter;

import static com.commerce.global.common.constants.ErrorStatusCode.INVALID_TOKEN;

@Getter
public class InvalidTokenAuthenticationException extends RuntimeException {

    private int status;
    private String message;

    public InvalidTokenAuthenticationException(String message) {
        this.status = INVALID_TOKEN;
        this.message = message;
    }

    public InvalidTokenAuthenticationException(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
