package com.yarosh.library.repository.jdbc;

import com.yarosh.library.repository.api.exception.RepositoryException;

public class JdbcRepositoryException extends RepositoryException {

    public JdbcRepositoryException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
