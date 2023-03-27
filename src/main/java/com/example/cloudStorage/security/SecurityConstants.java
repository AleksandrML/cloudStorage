package com.example.cloudStorage.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityConstants {

    public static String SECRET;
    public static final long EXPIRATION_TIME = 900_000; // 15 mins
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "auth-token";
    public static final String SIGN_UP_URL = "/api/user";

    @Value("${security.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

}
