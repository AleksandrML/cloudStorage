package com.example.cloudStorage.models;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String username;
    private String password;
}
