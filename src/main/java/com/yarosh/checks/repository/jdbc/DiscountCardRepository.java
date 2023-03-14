package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.DiscountCardEntity;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class DiscountCardRepository implements CrudRepository<DiscountCardEntity, Long> {

    private static final String SQL_SELECT_ALL = "";

    private final String url;
    private final String username;
    private final String password;

    public DiscountCardRepository(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
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

        try (Connection connection = DriverManager.getConnection(url, username, password);
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
