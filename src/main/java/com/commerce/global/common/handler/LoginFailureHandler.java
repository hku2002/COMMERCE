package com.commerce.global.common.handler;

import com.commerce.global.common.exception.BadCredentialsCustomException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {

    private static final String DEFAULT_FAILURE_MESSAGE = "아이디 또는 비밀번호가 일치하지 않습니다.";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println("test login failure handler");
        if (exception instanceof BadCredentialsException) {
            new BadCredentialsCustomException(DEFAULT_FAILURE_MESSAGE);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            new BadCredentialsCustomException(DEFAULT_FAILURE_MESSAGE);
        }
    }
}
