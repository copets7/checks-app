package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.entity.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class SqlExecutor<E extends Entity, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlExecutor.class);

    private static final int NO_ROWS_AFFECTED = 0;

    private final DataSource dataSource;
    private final Function<ResultSet, E> converterToEntity;
    private final Function<E, List<Object>> converterToParams;

    public SqlExecutor(DataSource dataSource,
                       Function<ResultSet, E> converterToEntity,
                       Function<E, List<Object>> converterToParams) {
        this.dataSource = dataSource;
        this.converterToEntity = converterToEntity;
        this.converterToParams = converterToParams;
    }

    public E insert(String sql, E entity, BiFunction<ResultSet, E, E> customConverterToEntity) {
        return execute(sql, converterToParams.apply(entity), preparedStatement ->
                performInsertFunction(entity, customConverterToEntity).apply(preparedStatement), Statement.RETURN_GENERATED_KEYS
        ).orElseThrow();
    }

    public E update(String sql, E entity) {
        return update(sql, entity, converterToParams);
    }

    @SuppressWarnings("UNCHECKED_CAST")
    public E update(String sql, E entity, Function<E, List<Object>> customConverterToParams) {
        List<Object> params = customConverterToParams.apply(entity);
        return execute(sql, params, preparedStatement -> {
            performUpdateConsumer((ID) params.get(params.size() - 1)).accept(preparedStatement);
            return entity;
        }).orElseThrow();
    }

    public List<E> selectAll(String sql) {
        return selectAll(sql, converterToEntity);
    }

    public List<E> selectAll(String sql, Function<ResultSet, E> customConverterToEntity) {
        List<E> entities = new ArrayList<>();

        executeWithoutResponse(sql, preparedStatement -> {
            performSelectAllConsumer(entities, customConverterToEntity).accept(preparedStatement);
            return null;
        });

        return entities;
    }

    public Optional<E> select(String sql, ID id) {
        return select(sql, id, converterToEntity);
    }

    public Optional<E> select(String sql, ID id, Function<ResultSet, E> customConverterToEntity) {
        return execute(sql, List.of(id), performSelectFunction(customConverterToEntity));
    }

    public void delete(String sql, ID id) {
        executeWithoutResponse(sql, List.of(id), preparedStatement -> {
            performUpdateConsumer(id).accept(preparedStatement);
            return null;
        });
    }

    public void executeWithoutResponse(String sql, Function<PreparedStatement, E> customConverterToEntity) {
        executeWithoutResponse(sql, List.of(), customConverterToEntity);
    }

    public void executeWithoutResponse(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity) {
        execute(sql, params, customConverterToEntity);
    }

    public Optional<E> execute(String sql, Function<PreparedStatement, E> customConverterToEntity, int statementKey) {
        return execute(sql, List.of(), customConverterToEntity, statementKey);
    }

    public Optional<E> execute(String sql, Function<PreparedStatement, E> customConverterToEntity) {
        return execute(sql, List.of(), customConverterToEntity, Statement.NO_GENERATED_KEYS);
    }

    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity) {
        return execute(sql, params, customConverterToEntity, Statement.NO_GENERATED_KEYS);
    }

    public Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity, int statementKey) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prepareStatement = connection.prepareStatement(sql, statementKey)
        ) {
            LOGGER.debug("SQL executing starts, params: {}", params);

            setParams(prepareStatement, params);
            Optional<E> result = Optional.ofNullable(customConverterToEntity.apply(prepareStatement));
            LOGGER.debug("SQL executing processed, result: {}", result);

            return result;
        } catch (SQLException e) {
            LOGGER.error("SQL executing failed, message: {}", e.getMessage());
            LOGGER.debug("SQL executing failed", e);
            throw new SqlExecutorException("SQL executing failed, e: {0}", e);
        }
    }

    private Function<PreparedStatement, E> performInsertFunction(E entity, BiFunction<ResultSet, E, E> customConverterToEntity) {
        return preparedStatement -> {
            try {
                preparedStatement.executeUpdate();

                try (ResultSet generatedKey = preparedStatement.getGeneratedKeys()) {
                    LOGGER.trace("SQL result set converting starts, metadata: {}", generatedKey.getMetaData());

                    if (!generatedKey.next()) {
                        LOGGER.error("There is no generated key after insert, entity: {}", entity);
                        throw new SqlExecutorException("There is no generated key after insert, entity: {0}", entity);
                    }
                    return customConverterToEntity.apply(generatedKey, entity);
                }

            } catch (SQLException e) {
                LOGGER.error("Exception during SQL insert, message: {}", e.getMessage());
                LOGGER.debug("Exception during SQL insert", e);
                throw new SqlExecutorException("Exception during SQL insert, e: {0}", e);
            }
        };
    }

    private Consumer<PreparedStatement> performSelectAllConsumer(List<E> result, Function<ResultSet, E> customConverterToEntity) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                LOGGER.trace("SQL result set converting starts, metadata: {}", resultSet.getMetaData());
                while (resultSet.next()) {
                    result.add(customConverterToEntity.apply(resultSet));
                }

            } catch (SQLException e) {
                LOGGER.error("Performing result set for SQL select all failed, message: {}", e.getMessage());
                LOGGER.debug("Performing result set for SQL select all failed", e);
                throw new SqlExecutorException("Performing result set for SQL select all failed, e: {0}", e);
            }
        };
    }

    private Function<PreparedStatement, E> performSelectFunction(Function<ResultSet, E> customConverterToEntity) {
        return preparedStatement -> {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                LOGGER.trace("SQL result set converting starts, metadata: {}", resultSet.getMetaData());
                return (resultSet.next()) ? customConverterToEntity.apply(resultSet) : null;

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
                Object param = params.get(i);
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
