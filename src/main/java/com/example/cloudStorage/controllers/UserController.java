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
//сделать: secret key move to envs, и далее основной функционал делать (корректные ответы по апи их,
// таблицы для юзеров по файлам отдельно, понять можно ли файлы прямо в постгре хранить, проработка апи), почистить ненужное