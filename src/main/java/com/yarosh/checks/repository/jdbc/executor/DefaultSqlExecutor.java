package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class DefaultSqlExecutor<E extends Entity, ID> implements SqlExecutor<E, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultSqlExecutor.class);

    private static final int NO_ROWS_AFFECTED = 0;

    private final DataSource dataSource;

    @Inject
    public DefaultSqlExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public E insert(String sql, E entity, Function<E, List<Object>> converterToParams, BiFunction<ResultSet, E, E> converterToEntity) {
        LOGGER.debug("Sql starts inserting, entity");

        return execute(sql, converterToParams.apply(entity), preparedStatement ->
                performInsertFunction(entity, converterToEntity).apply(preparedStatement), Statement.RETURN_GENERATED_KEYS
        ).orElseThrow();
    }

    @Override
    @SuppressWarnings("UNCHECKED_CAST")
    public E update(String sql, E entity, Function<E, List<Object>> converterToParams) {
        LOGGER.debug("Sql starts updating, entity");

        final List<Object> params = converterToParams.apply(entity);
        return execute(sql, params, preparedStatement -> {
            performUpdateConsumer((ID) params.get(params.size() - 1)).accept(preparedStatement);
            return entity;
        }).orElseThrow();
    }

    @Override
    public List<E> selectAll(String sql, Function<ResultSet, E> converterToEntity) {
        LOGGER.debug("Sql starts selecting all");

        final List<E> entities = new ArrayList<>();
        executeWithoutResponse(sql, preparedStatement -> {
            performSelectAllConsumer(entities, converterToEntity).accept(preparedStatement);
            return null;
        });

        return entities;
    }

    @Override
    public Optional<E> select(String sql, ID id, Function<ResultSet, E> converterToEntity) {
        LOGGER.debug("Sql starts selecting by ID");
        return execute(sql, List.of(id), performSelectFunction(converterToEntity));
    }

    @Override
    public void delete(String sql, ID id) {
        LOGGER.debug("Sql starts deleting");

        executeWithoutResponse(sql, List.of(id), preparedStatement -> {
            performUpdateConsumer(id).accept(preparedStatement);
            return null;
        });
    }

    @Override
    public void executeWithoutResponse(String sql, Function<PreparedStatement, E> converterToEntity) {
        executeWithoutResponse(sql, List.of(), converterToEntity);
    }

    @Override
    public void executeWithoutResponse(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity) {
        execute(sql, params, converterToEntity);
    }

    @Override
    public Optional<E> execute(String sql, Function<PreparedStatement, E> customConverterToEntity, int statementKey) {
        return execute(sql, List.of(), customConverterToEntity, statementKey);
    }

    @Override
    public Optional<E> execute(String sql, Function<PreparedStatement, E> converterToEntity) {
        return execute(sql, List.of(), converterToEntity, Statement.NO_GENERATED_KEYS);
    }

    @Override
    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity) {
        return execute(sql, params, converterToEntity, Statement.NO_GENERATED_KEYS);
    }

    @Override
    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity, int statementKey) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql, statementKey)
        ) {
            LOGGER.debug("SQL executing starts, params: {}", params);

            setParams(prepareStatement, params);
            final Optional<E> result = Optional.ofNullable(converterToEntity.apply(prepareStatement));
            LOGGER.debug("SQL executing processed, result: {}", result);

            return result;
        } catch (SQLException e) {
            LOGGER.error("SQL executing failed, message: {}", e.getMessage());
            LOGGER.debug("SQL executing failed", e);
            throw new SqlExecutorException("SQL executing failed, e: {0}", e);
        }
    }

    private Function<PreparedStatement, E> performInsertFunction(E entity, BiFunction<ResultSet, E, E> converterToEntity) {
        return preparedStatement -> {
            try {
                preparedStatement.executeUpdate();

                try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                    LOGGER.trace("SQL result set converting starts, metadata: {}", generatedKey.getMetaData());

                    if (!generatedKey.next()) {
                        LOGGER.error("There is no generated key after insert, entity: {}", entity);
                        throw new SqlExecutorException("There is no generated key after insert, entity: {0}", entity);
                    }
                    return converterToEntity.apply(generatedKey, entity);
                }

            } catch (SQLException e) {
                LOGGER.error("Exception during SQL insert, message: {}", e.getMessage());
                LOGGER.debug("Exception during SQL insert", e);
                throw new SqlExecutorException("Exception during SQL insert, e: {0}", e);
            }
        };
    }

    private Consumer<PreparedStatement> performSelectAllConsumer(List<E> result, Function<ResultSet, E> converterToEntity) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                LOGGER.trace("SQL result set converting starts, metadata: {}", resultSet.getMetaData());
                while (resultSet.next()) {
                    result.add(converterToEntity.apply(resultSet));
                }

            } catch (SQLException e) {
                LOGGER.error("Performing result set for SQL select all failed, message: {}", e.getMessage());
                LOGGER.debug("Performing result set for SQL select all failed", e);
                throw new SqlExecutorException("Performing result set for SQL select all failed, e: {0}", e);
            }
        };
    }

    private Function<PreparedStatement, E> performSelectFunction(Function<ResultSet, E> converterToEntity) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                LOGGER.trace("SQL result set converting starts, metadata: {}", resultSet.getMetaData());
                return (resultSet.next()) ? converterToEntity.apply(resultSet) : null;

            } catch (SQLException e) {
                LOGGER.error("Performing result set for SQL select failed, message: {}", e.getMessage());
                LOGGER.debug("Performing result set for SQL select failed", e);
                throw new SqlExecutorException("Performing result set for SQL select failed, e: {0}", e);
            }
        };
    }

    private Consumer<PreparedStatement> performUpdateConsumer(ID id) {
        return preparedStatement -> {
            try {
                int modifiedRows = preparedStatement.executeUpdate();

                if (modifiedRows == NO_ROWS_AFFECTED) {
                    LOGGER.error("There is no record with ID {}", id);
                    throw new SqlExecutorException("There is no record with ID {0}", id);
                }

                LOGGER.trace("SQL prepared statement update processed, modified rows: {}", modifiedRows);
            } catch (SQLException e) {
                LOGGER.error("Executing SQL update failed, message: {}", e.getMessage());
                LOGGER.debug("Executing SQL update failed", e);
                throw new SqlExecutorException("Executing SQL update failed, e: {0}", e);
            }
        };
    }

    private void setParams(PreparedStatement preparedStatement, List<Object> params) {
        try {
            for (int i = 0; i != params.size(); i++) {
                int paramIndex = i + 1;
                final Object param = params.get(i);
                preparedStatement.setObject(paramIndex, param);

                LOGGER.trace("SQL param was set, index: {}, param: {}", paramIndex, param);
            }
        } catch (SQLException e) {
            LOGGER.error("Setting params to prepared statement failed, message: {}", e.getMessage());
            LOGGER.debug("Setting params to prepared statement failed", e);
            throw new SqlExecutorException("Setting params to prepared statement failed, e: {0} ", e);
        }
    }
}
