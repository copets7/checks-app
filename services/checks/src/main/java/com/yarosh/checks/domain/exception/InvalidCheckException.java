package com.yarosh.checks.domain.exception;

import java.text.MessageFormat;

public class InvalidCheckException extends RuntimeException {

    public InvalidCheckException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
