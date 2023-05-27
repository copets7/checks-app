package com.yarosh.library.repository.api.exception;

public class RecordNotFoundException extends RepositoryException {

    public RecordNotFoundException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
