package com.yarosh.library.user.service;

import java.util.Optional;

public interface UserService<D> {

    Optional<D> findByUsername(String username);
}
