package com.yarosh.library.repository.pool;

import java.text.MessageFormat;

public class DatabaseConnectionPoolException extends RuntimeException {

    public DatabaseConnectionPoolException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
