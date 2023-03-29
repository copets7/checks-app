package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.entity.Entity;
import com.yarosh.checks.repository.pool.DatabaseConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class SqlExecutor<E extends Entity, ID> {

    private final DatabaseConnectionPool connectionPool;

    public SqlExecutor(DatabaseConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Optional<E> execute(String sql,
                               List<Object> params,
                               Function<PreparedStatement, E> resultSetExecutor,
                               int statementKey) {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql, statementKey);
        ) {
            setParams(prepareStatement, params);
            return Optional.ofNullable(resultSetExecutor.apply(prepareStatement));
        } catch (SQLException e) {
            throw new SqlExecutorException("SQL executing failed, e: {0} ", e);
        }
    }

    private void setParams(PreparedStatement preparedStatement, List<Object> params) {
        try {
            for (int i = 0; i != params.size(); i++) {
                int parameterIndex = i + 1;
                preparedStatement.setObject(parameterIndex, params.get(i));
            }
        } catch (SQLException e) {
            throw new SqlExecutorException("Setting params to prepared statement failed, e: {0} ", e);
        }
    }
}
