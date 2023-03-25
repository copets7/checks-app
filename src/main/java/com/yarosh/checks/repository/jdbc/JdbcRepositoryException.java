package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.RepositoryException;

public class JdbcRepositoryException extends RepositoryException {

    public JdbcRepositoryException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
