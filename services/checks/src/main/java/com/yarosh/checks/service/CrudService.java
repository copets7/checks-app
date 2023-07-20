package com.yarosh.checks.service;

import com.yarosh.checks.domain.Domain;
import com.yarosh.checks.domain.id.DomainId;
import com.yarosh.checks.domain.pagination.ContentPage;
import com.yarosh.checks.domain.pagination.ContentPageRequest;

import java.util.List;
import java.util.Optional;

public interface CrudService<D extends Domain, ID extends DomainId> {

    D add(D domain);

    /**
     * Returns object if it's not null, otherwise throws exception
     **/
    D getOrThrow(ID id);

    Optional<D> get(ID id);

    List<D> getAll();

    D update(D domain);

    void delete(ID id);

    ContentPage<D> findAllWithPagination(final ContentPageRequest request);
}
