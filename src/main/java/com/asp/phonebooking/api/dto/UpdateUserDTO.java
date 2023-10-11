package com.asp.phonebooking.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class UpdateUserDTO {

    @NotNull
    @Size(min = 5, max = 100)
    private final String password;

    @NotEmpty
    private final Set<UserRole> roles;

    public UpdateUserDTO(String password, Set<UserRole> roles) {
        this.password = password;
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }
}
