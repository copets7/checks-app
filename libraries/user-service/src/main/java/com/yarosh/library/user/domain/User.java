package com.yarosh.library.user.domain;

import java.util.List;

public class User {

    private long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private List<Role> roles;

    public User() {
    }

    public User(long id, String username, String firstname, String lastname, String password, List<Role> roles) {
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

    public List<Role> getRoles() {
        return roles;
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
}
