package com.example.cloudStorage.handler;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.cloudStorage.exceptions.BadCredentials;
import com.example.cloudStorage.exceptions.FilenameError;
import com.example.cloudStorage.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerController {

    @ExceptionHandler({FilenameError.class})
    public ResponseEntity<ErrorResponse> uploadError(RuntimeException exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 1), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({BadCredentials.class, TokenExpiredException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> credentialsError(Exception exception) {
        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 0), HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler({TokenExpiredException.class})
//    public ResponseEntity<ErrorResponse> tokenExpiredError(RuntimeException exception) {
//        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 0), HttpStatus.UNAUTHORIZED);
//    }

//    @ExceptionHandler({UsernameNotFoundException.class})
//    public ResponseEntity<ErrorResponse> userNotFoundError(UsernameNotFoundException exception) {
//        return new ResponseEntity<>(new ErrorResponse(exception.getMessage(), 0), HttpStatus.BAD_REQUEST);
//    }













    //    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
//    public ResponseEntity<BadCredentials> customForbiddenError(RuntimeException exception) {
//        return new ResponseEntity<>(new BadCredentials(exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }



//    @ExceptionHandler({InternalAuthenticationServiceException.class})
//    public ResponseEntity<BadCredentials> internalAuthError(InternalAuthenticationServiceException exception) {
//        return new ResponseEntity<>(new BadCredentials(exception.getMessage()), HttpStatus.BAD_REQUEST);
//    }



//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorCustomResponse> serverError(RuntimeException exception) {
//        return new ResponseEntity<>(new ErrorCustomResponse(exception.getMessage(), 1), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

}
