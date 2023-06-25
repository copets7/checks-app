package com.yarosh.library.user.service;

import com.yarosh.library.user.domain.Role;
import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.repository.UserRepository;
import com.yarosh.library.user.repository.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements UserService<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository<UserEntity> findByNameUserRepository;

    @Inject
    public UserServiceImpl(UserRepository<UserEntity> findByNameUserRepository) {
        this.findByNameUserRepository = findByNameUserRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        LOGGER.debug("Finding user with username {} started", username);
        final Optional<User> user = findByNameUserRepository.findByName(username).map(this::convertToUser);
        LOGGER.trace("User with username {} found, user: {}", username, user);

        return user;
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
