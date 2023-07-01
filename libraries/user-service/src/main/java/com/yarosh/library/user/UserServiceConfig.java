package com.yarosh.library.user;

import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.repository.UserRepository;
import com.yarosh.library.user.repository.entity.UserEntity;
import com.yarosh.library.user.service.UserService;
import com.yarosh.library.user.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.yarosh.library.user.repository")
public class UserServiceConfig {

    @Bean
    public UserService<User> findUserByUserNameService(UserRepository<UserEntity> findByNameRepository) {
        return new UserServiceImpl(findByNameRepository);
    }
}
