package com.yarosh.library.authentication.jwt.domain;

public record JwtResponse(String username, String token) {

    @Override
    public String toString() {
        return "JwtResponse{" +
                "username='" + username + '\'' +
                ", token = ********" + '\'' +
                '}';
    }
}
