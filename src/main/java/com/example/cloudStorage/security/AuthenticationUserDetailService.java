package com.example.cloudStorage.security;

import com.example.cloudStorage.models.ProjectUser;
import com.example.cloudStorage.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailService implements UserDetailsService {

    private final UserService userService;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ProjectUser projectUser = userService.readUserByUsername(username);
        if (projectUser == null) {
            throw new UsernameNotFoundException(username);
        }
        return new org.springframework.security.core.userdetails.User(projectUser.getUsername(), projectUser.getPassword(), Collections.emptyList());
    }
}
