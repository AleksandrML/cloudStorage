package com.example.cloudStorage.exceptions;

public class BadCredentials extends RuntimeException {
    public BadCredentials(String msg) {
        super(msg);
    }
}
