package com.yarosh.library.user.domain;

import java.text.MessageFormat;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
