package com.yarosh.library.authentication.jwt.service;

import org.springframework.security.core.AuthenticationException;

import java.text.MessageFormat;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
