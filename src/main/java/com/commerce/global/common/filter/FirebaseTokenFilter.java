package com.commerce.global.common.filter;

import com.commerce.global.common.exception.InvalidTokenAuthenticationException;
import com.commerce.global.common.token.JwtTokenManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.commerce.global.common.constants.CommonConstants.*;

@Order(1)
@Component
@RequiredArgsConstructor
public class FirebaseTokenFilter extends OncePerRequestFilter {

    private final FirebaseAuth firebaseAuth;
    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String token = resolveToken(request);
        try {
            firebaseAuth.verifyIdToken(token);
        } catch (FirebaseAuthException e) {
            throw new InvalidTokenAuthenticationException("Invalid Firebase Token");
        }

        if (StringUtils.hasText(token) && jwtTokenManager.validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenManager.getAuthentication(token));
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_TOKEN_BEGIN_INDEX);
        }
        return null;
    }
}
