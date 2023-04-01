package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.entity.Entity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class SqlExecutor<E extends Entity, ID> {

    private final DataSource dataSource;

    public SqlExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<E> selectAll(String sql, Function<ResultSet, E> resultSetExecutor) {
        List<E> entities = new ArrayList<>();

        executeWithoutResponse(sql, preparedStatement -> {
            performSelectAllConsumer(entities, resultSetExecutor).accept(preparedStatement);
            return null;
        });

        return entities;
    }

    public Optional<E> select(String sql, ID id, Function<ResultSet, E> resultSetExecutor) {
        return execute(sql, List.of(id), performSelectFunction(resultSetExecutor));
    }

    public void executeWithoutResponse(String sql, Function<PreparedStatement, E> resultSetExecutor) {
        execute(sql, List.of(), resultSetExecutor, Statement.NO_GENERATED_KEYS);
    }

    public Optional<E> execute(String sql, Function<PreparedStatement, E> resultSetExecutor, int statementKey) {
        return execute(sql, List.of(), resultSetExecutor, statementKey);
    }

    public Optional<E> execute(String sql, Function<PreparedStatement, E> resultSetExecutor) {
        return execute(sql, List.of(), resultSetExecutor, Statement.NO_GENERATED_KEYS);
    }

    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> resultSetExecutor) {
        return execute(sql, params, resultSetExecutor, Statement.NO_GENERATED_KEYS);
    }

    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> resultSetExecutor, int statementKey) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql, statementKey);
        ) {
            setParams(prepareStatement, params);
            return Optional.ofNullable(resultSetExecutor.apply(prepareStatement));
        } catch (SQLException e) {
            throw new SqlExecutorException("SQL executing failed, e: {0} ", e);
        }
    }

    private Consumer<PreparedStatement> performSelectAllConsumer(List<E> result, Function<ResultSet, E> resultSetExecutor) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    result.add(resultSetExecutor.apply(resultSet));
                }

            } catch (SQLException e) {
                throw new SqlExecutorException("Performing result set for SQL select all failed, e: {0}", e);
            }
        };
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

    private Function<PreparedStatement, E> performSelectFunction(Function<ResultSet, E> resultSetExecutor) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return (resultSet.next()) ? resultSetExecutor.apply(resultSet) : null;
            } catch (SQLException e) {
                throw new SqlExecutorException("Performing result set for SQL select failed, e: {0}", e);
            }
        };
    }
}
