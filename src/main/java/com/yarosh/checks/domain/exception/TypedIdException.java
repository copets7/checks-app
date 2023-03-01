package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class TypedIdException extends RuntimeException{

    public TypedIdException(Object id) {
        this("ID can't be null in {0}", id);
    }

    public TypedIdException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
