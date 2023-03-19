package com.example.cloudStorage.exceptions;

public class FilenameAlreadyExists extends RuntimeException {
    public FilenameAlreadyExists(String msg) {
        super(msg);
    }
}
