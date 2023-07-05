package com.yarosh.library.authentication.jwt.domain;

import org.apache.commons.lang3.StringUtils;

public record JwtResponse(String username, String token) {

    public JwtResponse(String username, String token) {
        this.username = username;
        this.token = token;

        validate();
    }

    @Override
    public String toString() {
        return "JwtResponse{" +
                "username='" + username + '\'' +
                ", token = ********" + '\'' +
                '}';
    }

    private void validate() {
        if (StringUtils.isBlank(username)) {
            throw new InvalidJwtUserException("Username is empty, {0}", this);
        }
        if (StringUtils.isBlank(token)) {
            throw new InvalidJwtUserException("Token is empty, {0}", this);
        }
    }
}
