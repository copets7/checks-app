package com.yarosh.library.repository.pool;

import com.yarosh.library.repository.pool.connection.PooledConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.IntStream;

public class DatabaseConnectionPool implements ConnectionPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConnectionPool.class);

    private final String url;
    private final String username;
    private final String password;
    private final int poolSize;

    private final Queue<Connection> freeConnections = new LinkedList<>();
    private final Queue<Connection> usedConnections = new LinkedList<>();

    private boolean isClosed;

    public DatabaseConnectionPool(final String url, final String username, final String password, final int poolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;

        init();
    }

    @Override
    public void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        freeConnections.add(connection);
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public Connection getConnection(String username, String password) {
        return getConnection();
    }

    @Override
    public Connection getConnection() {
        if (isClosed()) {
            LOGGER.error("Can't get SQL connection because pool is closed");
            throw new DatabaseConnectionPoolException("Can't get SQL connection because pool is closed");
        }

        Connection connection = freeConnections.poll();
        usedConnections.add(connection);

        return connection;
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
    public java.util.logging.Logger getParentLogger() {
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
            LOGGER.debug("Database connection pool closing starts, is closed: {}", isClosed);

            closeConnections(usedConnections);
            closeConnections(freeConnections);
            isClosed = true;

            LOGGER.debug("Database connection pool closing processed, is closed: {}", isClosed);
        }
    }

    private void init() {
        IntStream.of(poolSize).peek(i -> {
            try {
                Connection connection = DriverManager.getConnection(url, username, password);
                LOGGER.info("SQL connection created, number: {}", i);

                freeConnections.add(new PooledConnection(connection, this));
            } catch (SQLException e) {
                LOGGER.error("Connection pool init() failed, message: {}", e.getMessage());
                LOGGER.debug("Connection pool init() failed", e);
                throw new DatabaseConnectionPoolException("Connection pool init() failed, e: {0}", e);
            }
        }).sum();

        LOGGER.info("Database connection pool created, url: {}, username: #####, password: #####, pool size: {}", url, poolSize);
    }

    private void closeConnections(Queue<Connection> connections) {
        connections.stream()
                .map(connection -> (PooledConnection) connection)
                .map(PooledConnection::getConnection)
                .forEach(this::close);
    }

    private void close(Connection connection) {
        try {
            LOGGER.debug("SQL connection closing starts, is closed: {}", connection.isClosed());
            connection.close();
            LOGGER.trace("SQL connection closing processed, is closed: {}", connection.isClosed());
        } catch (SQLException e) {
            LOGGER.error("Exception during closing SQL connection, message: {}", e.getMessage());
            LOGGER.debug("Exception during closing SQL connection", e);
            throw new DatabaseConnectionPoolException("Exception during closing SQL connection, e: {0}", e);
        }
    }
}
