package com.commerce.global.common.config;

import com.commerce.global.common.filter.FirebaseTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static com.commerce.global.common.constants.CommonConstants.FIREBASE_AUTH_URL;

@Order(1)
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class FirebaseSecurityConfig extends WebSecurityConfigurerAdapter {

    private final FirebaseTokenFilter firebaseTokenFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher(FIREBASE_AUTH_URL)
                .addFilterBefore(firebaseTokenFilter, BasicAuthenticationFilter.class);
    }

}
