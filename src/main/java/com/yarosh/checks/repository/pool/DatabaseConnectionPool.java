package com.yarosh.checks.repository.pool;

import com.yarosh.checks.repository.pool.connection.PooledConnection;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class DatabaseConnectionPool implements DataSource, AutoCloseable {

    private final String url;
    private final String username;
    private final String password;
    private final int poolSize;

    private final BlockingQueue<Connection> freeConnections;
    private final BlockingQueue<Connection> usedConnections;

    private AtomicBoolean isClosed = new AtomicBoolean();

    public DatabaseConnectionPool(String url, String username, String password, int poolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;

        this.freeConnections = new ArrayBlockingQueue<>(poolSize);
        this.usedConnections = new ArrayBlockingQueue<>(poolSize);
        init();
    }

    public void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        freeConnections.add(connection);
    }

    public boolean isClosed() {
        return isClosed.get();
    }

    @Override
    public Connection getConnection(String username, String password) {
        return getConnection();
    }

    @Override
    public Connection getConnection() {
        if (isClosed()) {
            throw new DatabaseConnectionPoolException("Can't get SQL connection because pool is closed");
        }

        try {
            Connection connection = freeConnections.take();
            usedConnections.add(connection);

            return connection;
        } catch (InterruptedException e) {
            throw new DatabaseConnectionPoolException("Exception during getting connection from pool, e: {0}", e);
        }
    }

    @Override
    public PrintWriter getLogWriter() {
        throw new UnsupportedOperationException("getLogWriter is not supported");
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        throw new UnsupportedOperationException("setLogWriter is not supported");
    }

    @Override
    public void setLoginTimeout(int seconds) {
        throw new UnsupportedOperationException("setLoginTimeout is not supported");
    }

    @Override
    public int getLoginTimeout() {
        throw new UnsupportedOperationException("getLoginTimeout is not supported");
    }

    @Override
    public Logger getParentLogger() {
        throw new UnsupportedOperationException("getParentLogger is not supported");
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        throw new UnsupportedOperationException("unwrap is not supported");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        throw new UnsupportedOperationException("isWrapperFor is not supported");
    }

    @Override
    public void close() {
        if (!isClosed()) {
            closeConnections(usedConnections);
            closeConnections(freeConnections);
            isClosed.set(true);
        }
    }

    private void init() {
        IntStream.of(poolSize).peek(i -> {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                freeConnections.add(new PooledConnection(connection, this));
            } catch (SQLException e) {
                throw new DatabaseConnectionPoolException("Connection pool init() failed, e: {0}", e);
            }
        }).sum();
    }

    private void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new DatabaseConnectionPoolException("Exception during closing SQL connection, e: {0}", e);
        }
    }

    private void closeConnections(BlockingQueue<Connection> connections) {
        connections.stream()
                .map(connection -> (PooledConnection) connection)
                .map(PooledConnection::getConnection)
                .forEach(this::close);
    }

}
