package com.yarosh.library.authentication.jwt.domain;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public record JwtUser(Long id,
                      String username,
                      String firstname,
                      String lastname,
                      String password,
                      Collection<? extends GrantedAuthority> authorities) implements UserDetails {

    public JwtUser {
        validate();
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        throw new UnsupportedOperationException("JwtUser class doesn't support such method");
    }

    @Override
    public boolean isAccountNonLocked() {
        throw new UnsupportedOperationException("JwtUser class doesn't support such method");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        throw new UnsupportedOperationException("JwtUser class doesn't support such method");
    }

    @Override
    public boolean isEnabled() {
        throw new UnsupportedOperationException("JwtUser class doesn't support such method");
    }

    private void validate() {
        if (id == null) {
            throw new InvalidJwtUserException("Id is null, {0}", this);
        }
        if (StringUtils.isBlank(username)) {
            throw new InvalidJwtUserException("Username is empty, {0}", this);
        }
        if (StringUtils.isBlank(password)) {
            throw new InvalidJwtUserException("Password is empty, {0}", this);
        }
        if (authorities.isEmpty()) {
            throw new InvalidJwtUserException("Authorities is empty, {0}", this);
        }
    }
}
