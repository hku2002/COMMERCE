package com.commerce.global.common.filter;

import com.commerce.global.common.token.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.commerce.global.common.constants.CommonConstants.*;

@Order(2)
@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtTokenManager.resolveToken(request);
        if (StringUtils.hasText(jwt) && jwtTokenManager.validateToken(jwt)) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenManager.getAuthentication(jwt));
        }

        filterChain.doFilter(request, response);
    }

}
