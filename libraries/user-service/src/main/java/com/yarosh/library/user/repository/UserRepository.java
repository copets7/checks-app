package com.yarosh.library.user.repository;

import java.util.Optional;

public interface UserRepository<E> {

    Optional<E> findByName(String name);
}
