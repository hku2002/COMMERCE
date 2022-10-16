package com.commerce.global.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class CommonResponse<T> {

    private int status;
    private String message;
    private T response;

    public CommonResponse() {
        this.status = 200;
        this.message = "success";
        this.response = (T) "";
    }

    public CommonResponse(T response) {
        this.status = 200;
        this.message = "success";
        this.response = response;
    }

    @Builder
    public CommonResponse(int status, String message, T response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public static ResponseEntity<?> setResponse(Object response) {
        return ResponseEntity.ok().body(new CommonResponse<>(response));
    }

    public static ResponseEntity<?> setResponse() {
        return ResponseEntity.ok().body(new CommonResponse<>());
    }
}
