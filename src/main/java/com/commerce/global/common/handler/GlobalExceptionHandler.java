package com.commerce.global.common.handler;

import com.commerce.global.common.dto.ErrorResponseDto;
import com.commerce.global.common.exception.BadCredentialsCustomException;
import com.commerce.global.common.exception.BadRequestException;
import com.commerce.global.common.exception.InvalidTokenAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.commerce.global.common.constants.ErrorStatusCode.INTERNAL_SERVER_ERROR;
import static com.commerce.global.common.constants.ErrorStatusCode.METHOD_NOT_ALLOWED;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_MESSAGE = "서버에서 오류가 발생하였습니다.";

    /**
     * Exception Handler
     * @param e
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE));
    }

    /**
     * RuntimeException Handler
     * @param e
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ErrorResponseDto(INTERNAL_SERVER_ERROR, DEFAULT_MESSAGE));
    }

    /**
     * HttpRequestMethodNotSupportedException Handler (HTTP 메소드를 잘못 호출할 경우 처리)
     * @param e
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(new ErrorResponseDto(METHOD_NOT_ALLOWED, e.getMessage()));
    }

    /**
     * BadRequestException Handler (사용자 정의 Exception, client 에서 잘못된 요청을 보낼 경우 처리)
     * @param e
     */
    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ErrorResponseDto> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponseDto(e.getStatus(), e.getMessage()));
    }

    /**
     * InvalidTokenAuthenticationException Handler (사용자 정의 Exception, 토큰이 잘못되었을 경우 처리)
     * @param e
     */
    @ExceptionHandler(InvalidTokenAuthenticationException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidTokenAuthenticationException(InvalidTokenAuthenticationException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponseDto(e.getStatus(), e.getMessage()));
    }

    /**
     * BadCredentialsCustomException Handler (사용자 정의 Exception, 로그인 실패 처리)
     * @param e
     */
    @ExceptionHandler(BadCredentialsCustomException.class)
    protected ResponseEntity<ErrorResponseDto> handleInvalidTokenAuthenticationException(BadCredentialsCustomException e) {
        return ResponseEntity.status(e.getStatus()).body(new ErrorResponseDto(e.getStatus(), e.getMessage()));
    }

}
