package com.lec.spring.domain;

import lombok.Getter;

@Getter
public enum Auth {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_BLOCK("ROLE_BLOCK");

    private final String auth;

    Auth(String auth) {
        this.auth = auth;
    }
}
