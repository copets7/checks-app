package com.yarosh.checks.controller.security;

import com.yarosh.library.authentication.jwt.JwtConfigurerAdapter;
import com.yarosh.library.authentication.jwt.JwtSecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Import(JwtSecurityConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfigurerAdapter jwtConfigurerAdapter;

    public WebSecurityConfig(JwtConfigurerAdapter jwtConfigurerAdapter) {
        this.jwtConfigurerAdapter = jwtConfigurerAdapter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1.0/login").permitAll()
                .antMatchers("/api/v1.0/checks").hasAnyRole("ADMIN", "CASHIER")
                .anyRequest().authenticated()
                .and()
                .apply(jwtConfigurerAdapter);
    }
}
