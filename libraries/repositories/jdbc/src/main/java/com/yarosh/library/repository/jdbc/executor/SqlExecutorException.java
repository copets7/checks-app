package com.yarosh.library.repository.jdbc.executor;

import com.yarosh.library.repository.jdbc.JdbcRepositoryException;

public class SqlExecutorException extends JdbcRepositoryException {

    public SqlExecutorException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
