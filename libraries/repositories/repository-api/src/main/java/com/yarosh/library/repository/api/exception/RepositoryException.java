package com.yarosh.library.repository.api.exception;

import java.text.MessageFormat;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
