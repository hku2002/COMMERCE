package com.commerce.global.common.exception;

import lombok.Getter;

import static com.commerce.global.common.constants.ErrorStatusCode.BAD_REQUEST;

@Getter
public class BadCredentialsCustomException extends RuntimeException {

    private int status;
    private String message;

    public BadCredentialsCustomException(String message) {
        this.status = BAD_REQUEST;
        this.message = message;
    }

    public BadCredentialsCustomException(int status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }
}
