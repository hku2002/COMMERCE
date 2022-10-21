package com.commerce.global.common.exception;

import lombok.Getter;

import static com.commerce.global.common.constants.ErrorStatusCode.BAD_REQUEST;

@Getter
public class BadRequestException extends RuntimeException {

    private int status;
    private String message;

    public BadRequestException(String message) {
        this.status = BAD_REQUEST;
        this.message = message;
    }

    public BadRequestException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
