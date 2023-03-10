package com.yarosh.checks.service;

import com.yarosh.checks.domain.Domain;
import com.yarosh.checks.domain.id.DomainId;

import java.util.List;
import java.util.Optional;

public interface CrudService<D extends Domain, ID extends DomainId> {

    D add(D domain);

    Optional<D> get(ID id);

    List<D> getAll();

    D update(D domain);

    void delete(ID id);
}
