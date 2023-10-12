package com.asp.phonebooking.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePhoneDTO {

    @NotNull
    @Size(min = 3, max = 300)
    private final String name;

    @NotNull
    private final Long phoneModelId;

    @JsonCreator
    public CreatePhoneDTO(String name, Long phoneModelId) {
        this.name = name;
        this.phoneModelId = phoneModelId;
    }

    public String getName() {
        return name;
    }

    public Long getPhoneModelId() {
        return phoneModelId;
    }
}
