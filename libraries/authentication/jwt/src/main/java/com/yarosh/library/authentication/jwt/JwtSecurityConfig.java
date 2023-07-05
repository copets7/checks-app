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
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan("com.yarosh.library.authentication.jwt.service")
@Import(UserServiceConfig.class)
@Order(1)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public JwtSecurityConfig(UserService<User> userService) {
        this.userDetailsService = new JwtUserDetailsService(userService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtConfigurerAdapter jwtConfigurerAdapter(JwtTokenFilter jwtTokenFilter) {
        return new JwtConfigurerAdapter(jwtTokenFilter);
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter(JwtTokenService jwtTokenService) {
        return new JwtTokenFilter(jwtTokenService);
    }

    @Bean
    public JwtTokenService jwtTokenService(@Value("${jwt.token.secret}") String secret,
                                           @Value("${jwt.token.validity}") String validityInMilliSeconds,
                                           UserDetailsService userDetailsService,
                                           UserService<User> userService) {
        return new JwtTokenService(secret, Long.parseLong(validityInMilliSeconds), userDetailsService, userService);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService<User> userService) {
        return new JwtUserDetailsService(userService);
    }
}
