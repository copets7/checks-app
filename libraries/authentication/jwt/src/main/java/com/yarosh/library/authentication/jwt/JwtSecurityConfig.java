package com.yarosh.library.authentication.jwt;

import com.yarosh.library.authentication.jwt.service.JwtTokenService;
import com.yarosh.library.authentication.jwt.service.JwtUserDetailsService;
import com.yarosh.library.user.UserServiceConfig;
import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@ComponentScan("com.yarosh.library.authentication.jwt.service")
@Import(UserServiceConfig.class)
public class JwtSecurityConfig {

    @Bean
    public JwtTokenFilter jwtTokenFilter(JwtTokenService jwtTokenService) {
        return new JwtTokenFilter(jwtTokenService);
    }

    @Bean
    public JwtTokenService jwtTokenService(AuthenticationManager authenticationManager,
                                           @Value("${jwt.token.secret}") String secret,
                                           @Value("${jwt.token.validity}") String validityInMilliSeconds,
                                           UserDetailsService userDetailsService,
                                           UserService<User> userService) {
        return new JwtTokenService(authenticationManager, secret, Long.parseLong(validityInMilliSeconds), userDetailsService, userService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService<User> userService) {
        return new JwtUserDetailsService(userService);
    }
}
