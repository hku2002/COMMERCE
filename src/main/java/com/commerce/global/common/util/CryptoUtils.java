package com.commerce.global.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtils {

    public static String encrypt(final String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    public static boolean matches(final String password, final String encodedPassword) {
        return new BCryptPasswordEncoder().matches(password, encodedPassword);
    }
}
