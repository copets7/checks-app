package com.yarosh.library.user.domain;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public record User(Long id, String username, String firstname, String lastname, String password, List<Role> roles) {

    public User {
        validate();
    }

    @Override
    public String toString() {
        return "User{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", password=****************" + '\'' +
               ", roles=" + roles +
               '}';
    }

    private void validate() {
        if (id == null) {
            throw new InvalidUserException("Id is null, {0}", this);
        }
        if (StringUtils.isBlank(username)) {
            throw new InvalidUserException("Username is empty, {0}", this);
        }
        if (StringUtils.isBlank(password)) {
            throw new InvalidUserException("Password is empty, {0}", this);
        }
        if (roles.isEmpty()) {
            throw new InvalidUserException("Authorities is empty, {0}", this);
        }
    }
}
