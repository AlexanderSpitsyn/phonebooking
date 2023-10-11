package com.asp.phonebooking.api.dto;

import java.util.Set;

public class ReadUserDTO {

    private final String username;
    private final Set<UserRole> roles;

    public ReadUserDTO(String username, Set<UserRole> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}
