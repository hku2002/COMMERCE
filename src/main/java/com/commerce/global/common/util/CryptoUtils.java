package com.commerce.global.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CryptoUtils {

    public static String passwordEncoder(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean matches(final String password, final String encodedPassword) {
        return new BCryptPasswordEncoder().matches(password, encodedPassword);
    }
}
