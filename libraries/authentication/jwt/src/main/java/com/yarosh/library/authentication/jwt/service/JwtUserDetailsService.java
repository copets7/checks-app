package com.yarosh.library.authentication.jwt.service;

import com.yarosh.library.authentication.jwt.domain.JwtUser;
import com.yarosh.library.user.domain.User;
import com.yarosh.library.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.text.MessageFormat;

public class JwtUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);

    private final UserService<User> userService;

    public JwtUserDetailsService(UserService<User> userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByUsername(username)
                .map(this::converToUserDetails)
                .orElseThrow(() -> {
                    LOGGER.debug("User with username {} not found", username);
                    return new UsernameNotFoundException(MessageFormat.format("User with username {0} not found", username));
                });
    }

    private UserDetails converToUserDetails(User user) {
        return new JwtUser(
                user.id(),
                user.username(),
                user.firstname(),
                user.lastname(),
                user.password(),
                user.roles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
        );
    }
}
