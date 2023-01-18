package com.yarosh.checks.repository;

import java.util.List;

public interface CrudRepository<E, ID> {

    E insert(E entity);
    E find(E entity);
    List<E> findAll();
    E update(E entity);
    void delete(ID id);
}
