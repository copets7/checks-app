package com.yarosh.library.user.repository;

import com.yarosh.library.repository.api.exception.RepositoryException;

public class UserRepositoryException extends RepositoryException {

    public UserRepositoryException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
