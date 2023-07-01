package com.yarosh.library.authentication.jwt.controller.view;

public record JwtResponseView(String username, String token) {

    @Override
    public String toString() {
        return "JwtResponseView{" +
                "username='" + username + '\'' +
                ", token= **************" + '\'' +
                '}';
    }
}
