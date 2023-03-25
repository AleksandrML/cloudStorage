package com.example.cloudStorage.exceptions;

public class FilenameError extends RuntimeException {
    public FilenameError(String msg) {
        super(msg);
    }
}
