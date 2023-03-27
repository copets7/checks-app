package com.yarosh.checks.repository.jdbc;

public class SqlExecutorException extends JdbcRepositoryException{

    public SqlExecutorException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
