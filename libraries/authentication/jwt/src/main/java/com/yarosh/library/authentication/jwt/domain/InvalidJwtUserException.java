package com.yarosh.library.authentication.jwt.domain;

import java.text.MessageFormat;

public class InvalidJwtUserException extends RuntimeException {

    public InvalidJwtUserException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
