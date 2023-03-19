package com.example.cloudStorage.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.cloudStorage.models.ProjectUser;
import com.example.cloudStorage.models.UserCreateRequest;
import com.example.cloudStorage.repositories.UserRepository;
import com.example.cloudStorage.security.SecurityConstants;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ProjectUser readUserByUsername (String username) {
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void createUser(UserCreateRequest userCreateRequest) {
        ProjectUser projectUser = new ProjectUser();
        Optional<ProjectUser> byUsername = userRepository.findByUsername(userCreateRequest.getUsername());
        if (byUsername.isPresent()) {
            throw new RuntimeException("User already registered. Please use different username.");
        }
        projectUser.setUsername(userCreateRequest.getUsername());
        projectUser.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        userRepository.save(projectUser);
    }

    public static String getUserName(String token) {
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getSubject();
        return user;
    }

}
