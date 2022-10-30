package com.commerce.global.common.config;

import com.commerce.global.common.filter.JwtTokenFilter;
import com.commerce.global.common.token.JwtTokenManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private JwtTokenManager jwtTokenManager;

    public JwtSecurityConfig(JwtTokenManager jwtTokenManager) {
        this.jwtTokenManager = jwtTokenManager;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) {
        JwtTokenFilter customJwtFilter = new JwtTokenFilter(jwtTokenManager);
        httpSecurity.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
