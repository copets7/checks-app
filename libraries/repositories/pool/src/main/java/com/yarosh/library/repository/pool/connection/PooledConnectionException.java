package com.yarosh.library.repository.pool.connection;

import java.text.MessageFormat;

public class PooledConnectionException extends RuntimeException {

    public PooledConnectionException(String templateMessage, Object... params) {
        super(MessageFormat.format(templateMessage, params));
    }
}
