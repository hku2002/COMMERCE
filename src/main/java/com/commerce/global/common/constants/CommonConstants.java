package com.commerce.global.common.constants;

public class CommonConstants {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public final static String BEARER_PREFIX = "Bearer ";
    public final static int BEARER_TOKEN_BEGIN_INDEX = 7;
    public static final long REFRESH_TOKEN_TIME = 1000L * 60 * 60 * 24 * 365; // 1년
    public static final String FIREBASE_AUTH_URL = "/v1/auth/firebase";
}
