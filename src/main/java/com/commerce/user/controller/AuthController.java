package com.commerce.user.controller;

import com.commerce.global.common.CommonResponse;
import com.commerce.user.dto.LoginDto;
import com.commerce.user.service.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final LoginServiceImpl loginServiceImpl;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        return CommonResponse.setResponse(loginServiceImpl.login(loginDto));
    }
}
