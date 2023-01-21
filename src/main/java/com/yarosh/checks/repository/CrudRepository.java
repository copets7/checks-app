package com.yarosh.checks.repository;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E, ID> {

    E insert(E entity);
    Optional<E> find(ID id);
    List<E> findAll();
    E update(E entity);
    void delete(ID id);
}
