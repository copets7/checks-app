package com.yarosh.library.repository.executor;

import com.yarosh.library.repository.api.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface SqlExecutor<E extends Entity, ID> {

    int GENERATED_KEY_COLUMN_NUMBER = 1;

    E insert(String sql, E entity, Function<E, List<Object>> converterToParams, BiFunction<ResultSet, E, E> converterToEntity);

    E update(String sql, E entity, Function<E, List<Object>> converterToParams);

    List<E> selectAll(String sql, Function<ResultSet, E> converterToEntity);

    Optional<E> select(String sql, ID id, Function<ResultSet, E> converterToEntity);

    void delete(String sql, ID id);

    void executeWithoutResponse(String sql, Function<PreparedStatement, E> converterToEntity);

    void executeWithoutResponse(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity);

    Optional<E> execute(String sql, Function<PreparedStatement, E> converterToEntity, int statementKey);

    Optional<E> execute(String sql, Function<PreparedStatement, E> converterToEntity);

    Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity);

    Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> converterToEntity, int statementKey);
}
