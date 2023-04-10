package com.yarosh.checks.repository.jdbc;

import com.yarosh.checks.repository.CrudRepository;
import com.yarosh.checks.repository.entity.CheckEntity;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;

import java.util.List;
import java.util.Optional;

public class JdbcCheckRepository implements CrudRepository<CheckEntity, Long> {

    private final DatabaseConnectionPool connectionPool;

    public JdbcCheckRepository(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public CheckEntity insert(CheckEntity check) {
        return null;
    }

    @Override
    public Optional<CheckEntity> select(Long id) {
        return Optional.empty();
    }

    @Override
    public List<CheckEntity> selectAll() {
        return null;
    }

    @Override
    public CheckEntity update(CheckEntity check) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
