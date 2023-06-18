package com.yarosh.library.user.repository;

import com.yarosh.library.user.repository.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository implements FindByNameRepository<UserEntity> {

    private static final String SQL_SELECT_BY_USERNAME = """
        SELECT u.id, u.username, u.firstname, u.lastname, u.password, r.name FROM users as u
        JOIN roles as r on JSON_CONTAINS(roles, CAST(r.id as JSON))
        """;

    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<UserEntity> findByName(String name) {
        try (Connection connection = dataSource.getConnection()){

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
