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
    public String uploadFileQuery(@RequestHeader("auth-token") String token) {
        blackListedTokensRepository.addBlacklistedHeaderToken(token);
        System.out.println(token);
        return "done";
    }

}
//сделать: secret key move to envs, проработать тела ответов основных и ошибок, добавить эксепшны и контроллер для них,
// тестировать с фронтом,
// почистить ненужное, юнит тесты и докер контейнер тесты

//tokenExpiredException ловить

//рассмотреть во время гетЛист работать сразу только с нужными полями (не выгружать файлы) - после тестов всех только с фронтом и прочее