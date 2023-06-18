package com.yarosh.library.user.repository;

import java.util.Optional;

public interface FindByNameRepository<E> {

    Optional<E> findByName(String name);
}
