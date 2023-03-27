package com.example.cloudStorage.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.cloudStorage.exceptions.BadCredentials;
import com.example.cloudStorage.exceptions.FilenameError;
import com.example.cloudStorage.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler({FilenameError.class})
    public ResponseEntity<ErrorResponse> uploadError(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 1), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentials.class, TokenExpiredException.class, UsernameNotFoundException.class,
            InternalAuthenticationServiceException.class})
    public ResponseEntity<ErrorResponse> credentialsError(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 0), HttpStatus.UNAUTHORIZED);
    }

}
