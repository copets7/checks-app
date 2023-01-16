package com.yarosh.checks.service;

import java.util.List;

public interface CrudService<D, ID> {
    D add(D domain);
    D get(D domain);
    List<D> getAll();
    D update(D domain);
    void delete(ID id);

}
