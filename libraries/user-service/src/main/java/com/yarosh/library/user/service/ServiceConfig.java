package com.yarosh.library.user.service;

import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.repository.UserRepository;
import com.yarosh.library.user.repository.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public UserService<User> findUserByUserNameService(UserRepository<UserEntity> findByNameRepository) {
        return new UserServiceImpl(findByNameRepository);
    }
}
