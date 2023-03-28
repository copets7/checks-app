package com.yarosh.checks.repository.pool;

import com.yarosh.checks.repository.pool.connection.PooledConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.IntStream;

public class DatabaseConnectionPool implements AutoCloseable {

    private final String url;
    private final String username;
    private final String password;
    private final int poolSize;

    private final BlockingQueue<Connection> freeConnections;
    private final BlockingQueue<Connection> usedConnections;

    public DatabaseConnectionPool(String url, String username, String password, int poolSize) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;

        this.freeConnections = new ArrayBlockingQueue<>(poolSize);
        this.usedConnections = new ArrayBlockingQueue<>(poolSize);
        init();
    }

    public Connection getConnection() {
        try {
            Connection connection = freeConnections.take();
            usedConnections.add(connection);

            return connection;
        } catch (InterruptedException e) {
            throw new DatabaseConnectionPoolException("Exception during getting connection from pool, e: {0}", e);
        }
    }

    public void releaseConnection(Connection connection) {
        usedConnections.remove(connection);
        freeConnections.add(connection);
    }

    @Override
    public void close() {
        closeConnections(usedConnections);
        closeConnections(freeConnections);
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