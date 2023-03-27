package com.example.cloudStorage.controllers;

import com.example.cloudStorage.models.UserCreateRequest;
import com.example.cloudStorage.repositories.BlackListedTokensRepository;
import com.example.cloudStorage.security.SecurityConstants;
import com.example.cloudStorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final BlackListedTokensRepository blackListedTokensRepository;

    @PostMapping(SecurityConstants.SIGN_UP_URL)
    public ResponseEntity createUser (@RequestBody UserCreateRequest userCreateRequest) {
        userService.createUser(userCreateRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/logout")
    public ResponseEntity uploadFileQuery(@RequestHeader("auth-token") String token) {
        blackListedTokensRepository.addBlacklistedHeaderToken(token);
        return ResponseEntity.ok().build();
    }

}
