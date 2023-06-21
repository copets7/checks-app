package com.yarosh.library.user.repository;

import com.yarosh.library.user.repository.entity.UserEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RepositoryConfig {

    @Bean
    public UserRepository<UserEntity> findByNameUserRepository(DataSource dataSource) {
        return new UserRepositoryImpl(dataSource);
    }
}
