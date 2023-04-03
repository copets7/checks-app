package com.yarosh.checks.repository;

import com.yarosh.checks.repository.entity.Entity;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E extends Entity, ID> {

    E insert(E entity);

    Optional<E> select(ID id);

    List<E> selectAll();

    E update(E entity);

    void delete(ID id);
}
