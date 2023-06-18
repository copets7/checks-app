package com.yarosh.library.user.repository.entity;

import java.util.List;

public class UserEntity {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private List<RoleEntity> role;

    public UserEntity() {
    }

    public UserEntity(long id, String username, String firstname, String lastname, String password, List<RoleEntity> role) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
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

    public List<RoleEntity> getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password=****************" + '\'' +
                ", role=" + role +
                '}';
    }
}
