package com.commerce.user.service;

import com.commerce.global.common.token.JwtTokenManager;
import com.commerce.user.domain.RefreshToken;
import com.commerce.user.dto.LoginDto;
import com.commerce.user.dto.LoginResponseDto;
import com.commerce.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.commerce.global.common.constants.CommonConstants.REFRESH_TOKEN_TIME;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginServiceImpl {

    private final JwtTokenManager jwtTokenManager;
    private final RefreshTokenRepository refreshTokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 로그인 처리
     * @param loginDto
     */
    @Transactional
    public LoginResponseDto login(final LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String accessToken = jwtTokenManager.createAccessToken(authentication, loginDto.getUserId());
        final String refreshToken = jwtTokenManager.createRefreshToken(authentication, loginDto.getUserId());
        saveRefreshToken(loginDto.getUserId(), refreshToken);
        setAuthorizationHeader(accessToken);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(loginDto.getUserId())
                .username(authentication.getName())
                .build();
    }

    private void saveRefreshToken(String userId, String refreshToken) {
        refreshTokenRepository.save(RefreshToken.builder()
                .userId(userId)
                .refreshToken(refreshToken)
                .expiredTime(LocalDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis() + REFRESH_TOKEN_TIME)
                , ZoneId.systemDefault()))
                .build());
    }

    private void setAuthorizationHeader(String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
    }
}
