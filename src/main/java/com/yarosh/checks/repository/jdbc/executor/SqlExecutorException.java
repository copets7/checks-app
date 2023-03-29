package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.jdbc.JdbcRepositoryException;

public class SqlExecutorException extends JdbcRepositoryException {

    public SqlExecutorException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
