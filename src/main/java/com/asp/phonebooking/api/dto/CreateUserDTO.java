package com.asp.phonebooking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;

public class CreateUserDTO extends UpdateUserDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private final String username;

    public CreateUserDTO( String username, String password, Set<UserRole> roles) {
        super(password, roles);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
