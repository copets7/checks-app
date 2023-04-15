package com.yarosh.checks.repository.jdbc.executor;

import com.yarosh.checks.repository.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface SqlExecutor<E extends Entity, ID> {

    E insert(String sql, E entity, BiFunction<ResultSet, E, E> customConverterToEntity, Function<E, List<Object>> converterToParams);

    E update(String sql, E entity, Function<E, List<Object>> customConverterToParams);

    List<E> selectAll(String sql, Function<ResultSet, E> customConverterToEntity);

    Optional<E> select(String sql, ID id, Function<ResultSet, E> customConverterToEntity);

    void delete(String sql, ID id);

    void executeWithoutResponse(String sql, Function<PreparedStatement, E> customConverterToEntity);

    void executeWithoutResponse(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity);

    Optional<E> execute(String sql, Function<PreparedStatement, E> customConverterToEntity, int statementKey);

    Optional<E> execute(String sql, Function<PreparedStatement, E> customConverterToEntity);

    Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity);

    Optional<E> execute(String sql, List<Object> params, Function<PreparedStatement, E> customConverterToEntity, int statementKey);
}
