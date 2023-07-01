package com.yarosh.library.authentication.jwt.controller;

import com.yarosh.library.authentication.jwt.service.JwtAuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class JwtAuthenticationHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<String> handleUsernameNotFoundException(final UsernameNotFoundException e) {
        LOGGER.debug("UsernameNotFoundException was handled, message {}", e.getMessage());
        LOGGER.trace("UsernameNotFoundException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    protected ResponseEntity<String> handleJwtAuthenticationException(final JwtAuthenticationException e) {
        LOGGER.debug("JwtAuthenticationException was handled, message {}", e.getMessage());
        LOGGER.trace("JwtAuthenticationException was handled", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.PRECONDITION_FAILED);
    }

}
