package com.yarosh.library.repository.executor;

import com.yarosh.library.repository.api.exception.RepositoryException;

public class SqlExecutorException extends RepositoryException {

    public SqlExecutorException(String templateMessage, Object... params) {
        super(templateMessage, params);
    }
}
