package com.yarosh.library.authentication.jwt.service;

import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class ServiceConfig {

    @Bean
    public JwtTokenService jwtTokenService(@Value("${jwt.token.secret}") String secret,
                                           @Value("${jwt.token.validity}") String validityInMilliSeconds,
                                           UserDetailsService userDetailsService) {
        return new JwtTokenService(secret, Long.parseLong(validityInMilliSeconds), userDetailsService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService<User> userService) {
        return new JwtUserDetailsService(userService);
    }
}
