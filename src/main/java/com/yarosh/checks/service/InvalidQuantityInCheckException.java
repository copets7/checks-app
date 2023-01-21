package com.yarosh.checks.service;

import java.text.MessageFormat;

public class InvalidQuantityInCheckException extends RuntimeException {

    public InvalidQuantityInCheckException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
