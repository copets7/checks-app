package com.yarosh.library.repository.api;

import com.yarosh.library.repository.api.entity.BaseEntity;
import com.yarosh.library.repository.api.pagination.DatabasePage;
import com.yarosh.library.repository.api.pagination.DatabasePageRequest;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<E extends BaseEntity, ID> {

    E insert(E entity);

    Optional<E> select(ID id);

    List<E> selectAll();

    E update(E entity);

    void delete(ID id);

    DatabasePage<E> findAllWithPagination(final DatabasePageRequest request);
}
