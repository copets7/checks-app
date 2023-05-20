package com.yarosh.checks.repository.exception;

public class RecordNotFoundException extends RepositoryException {

    public RecordNotFoundException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
