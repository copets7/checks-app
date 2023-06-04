package com.yarosh.checks.controller.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new AppBasicAuthenticationEntryPoint();

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.inMemoryAuthentication()
                .withUser("Cashier")
                .password("{noop}pass")
                .authorities("ROLE_USER");
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.csrf()
                .disable()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthenticationEntryPoint);
    }
}
