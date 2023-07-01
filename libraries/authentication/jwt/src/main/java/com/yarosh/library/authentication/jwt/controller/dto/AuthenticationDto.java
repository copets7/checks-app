package com.yarosh.library.authentication.jwt.controller.dto;

public record AuthenticationDto(String username, String password) {

    @Override
    public String toString() {
        return "AuthenticationDto{" +
                "username='" + username + '\'' +
                ", password= *********'" + '\'' +
                '}';
    }
}
