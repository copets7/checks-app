package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class TypedIdException extends RuntimeException{

    public TypedIdException(final String templateMessage, final Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
