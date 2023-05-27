package com.yarosh.library.repository.pool;

import javax.sql.DataSource;
import java.sql.Connection;

public interface ConnectionPool extends DataSource, AutoCloseable {

    void releaseConnection(Connection connection);

    boolean isClosed();
}
