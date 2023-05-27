package com.yarosh.library.repository.pool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

public class SynchronizedDatabaseConnectionPool implements ConnectionPool {

    private final ConnectionPool pool;

    public SynchronizedDatabaseConnectionPool(final ConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public void releaseConnection(Connection connection) {
        synchronized (pool) {
            pool.releaseConnection(connection);
        }
    }

    @Override
    public boolean isClosed() {
        synchronized (pool) {
            return pool.isClosed();
        }
    }

    @Override
    public void close() throws Exception {
        synchronized (pool) {
            pool.close();
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        synchronized (pool) {
            return pool.getConnection();
        }
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        synchronized (pool) {
            return pool.getConnection(username, password);
        }
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        synchronized (pool) {
            return pool.getLogWriter();
        }
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        synchronized (pool) {
            pool.setLogWriter(out);
        }
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        synchronized (pool) {
            pool.setLoginTimeout(seconds);
        }
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        synchronized (pool) {
            return pool.getLoginTimeout();
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        synchronized (pool) {
            return pool.getParentLogger();
        }
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        synchronized (pool) {
            return pool.unwrap(iface);
        }
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        synchronized (pool) {
            return pool.isWrapperFor(iface);
        }
    }
}
