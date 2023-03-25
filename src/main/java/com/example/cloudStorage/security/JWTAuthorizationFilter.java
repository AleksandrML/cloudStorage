package com.example.cloudStorage.security;

import com.example.cloudStorage.exceptions.BadCredentials;
import com.example.cloudStorage.repositories.BlackListedTokensRepository;
import com.example.cloudStorage.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private BlackListedTokensRepository blackListedTokensRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, BlackListedTokensRepository blackListedTokensRepository) {
        super(authenticationManager);
        this.blackListedTokensRepository = blackListedTokensRepository;
    }

    @Override protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        if (blackListedTokensRepository.getBlacklistedHeaderTokens().contains(header)) {
            throw new BadCredentials("your token is blacklisted as being logout");
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }


    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token != null) {
            // parse the token.
            String user = UserService.getUserName(token);

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }
            return null;
        }
        return null;
    }

}
