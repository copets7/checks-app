package com.yarosh.library.user.service;

import com.yarosh.library.user.domain.Role;
import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.repository.UserRepository;
import com.yarosh.library.user.repository.entity.UserEntity;

import javax.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements UserService<User> {

    private final UserRepository<UserEntity> findByNameUserRepository;

    @Inject
    public UserServiceImpl(UserRepository<UserEntity> findByNameUserRepository) {
        this.findByNameUserRepository = findByNameUserRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return findByNameUserRepository.findByName(username).map(this::convertToUser);
    }

    private User convertToUser(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUsername(),
                entity.getFirstname(),
                entity.getLastname(),
                entity.getPassword(),
                entity.getRoles().stream().map(roleEntity -> new Role(roleEntity.getId(), roleEntity.getName())).toList()
        );
    }
}
