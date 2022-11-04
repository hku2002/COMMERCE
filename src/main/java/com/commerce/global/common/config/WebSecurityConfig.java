package com.commerce.global.common.config;

import com.commerce.global.common.entrypoint.JwtAuthenticationEntryPoint;
import com.commerce.global.common.filter.FirebaseTokenFilter;
import com.commerce.global.common.filter.JwtTokenFilter;
import com.commerce.global.common.handler.JwtAccessDeniedHandler;
import com.commerce.global.common.token.JwtTokenManager;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.commerce.global.common.constants.CommonConstants.FIREBASE_AUTH_URL;

@Order(2)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenManager jwtTokenManager;
    private final JwtTokenFilter jwtTokenFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final FirebaseAuth firebaseAuth;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/v1/orders",
                        "/v1/order",
                        "/v1/order/**",
                        "/v1/carts",
                        "/v1/cart",
                        "/v1/cart/**")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .disable();
    }

    @Bean
    public FilterRegistrationBean firebaseTokenFilterRegistrationBean(FirebaseTokenFilter firebaseTokenFilter) {
        System.out.println("bean test2");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(firebaseTokenFilter);
        filterRegistrationBean.addUrlPatterns(FIREBASE_AUTH_URL);
        return filterRegistrationBean;
    }
}
