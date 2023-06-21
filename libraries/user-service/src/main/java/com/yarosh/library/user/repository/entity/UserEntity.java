package com.yarosh.library.user.repository.entity;

import com.yarosh.library.repository.api.entity.Entity;

import java.util.List;

public class UserEntity implements Entity {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private List<RoleEntity> roles;

    public UserEntity() {
    }

    public UserEntity(long id, String username, String firstname, String lastname, String password, List<RoleEntity> roles) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getPassword() {
        return password;
    }

    public List<RoleEntity> getRoles() {
        return roles;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
               "id=" + id +
               ", username='" + username + '\'' +
               ", firstname='" + firstname + '\'' +
               ", lastname='" + lastname + '\'' +
               ", password=****************" + '\'' +
               ", roles=" + roles +
               '}';
    }
}
