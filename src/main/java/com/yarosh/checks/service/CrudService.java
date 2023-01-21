package com.yarosh.checks.service;

import java.util.List;
import java.util.Optional;

public interface CrudService<D, ID> {
    D add(D domain);
    Optional<D> get(ID id);
    List<D> getAll();
    D update(D domain);
    void delete(ID id);

}
