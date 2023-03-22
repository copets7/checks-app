package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class DiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final String SQL_SELECT_ALL = "";

    private final DatabaseConnectionPool connectionPool;

    public DiscountCardRepository(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public DiscountCardEntity insert(DiscountCardEntity card) {
        return null;
    }

    @Override
    public Optional<DiscountCardEntity> find(Long id) {
        return Optional.empty();
    }

    @Override
    public List<DiscountCardEntity> findAll() {

        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL);
        ) {
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public DiscountCardEntity update(DiscountCardEntity card) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
