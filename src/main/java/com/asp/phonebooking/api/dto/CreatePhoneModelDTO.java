package com.asp.phonebooking.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePhoneModelDTO {

    @NotNull
    @Size(min = 3, max = 300)
    private final String name;

    @JsonCreator
    public CreatePhoneModelDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
