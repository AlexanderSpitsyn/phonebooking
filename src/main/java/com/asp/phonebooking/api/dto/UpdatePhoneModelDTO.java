package com.asp.phonebooking.api.dto;

import static java.util.Objects.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePhoneModelDTO {

    @Schema(hidden = true)
    private Long id;

    @NotNull
    @Size(min = 3, max = 300)
    private final String name;

    @JsonCreator
    public UpdatePhoneModelDTO(String name) {
        this.name = name;
    }

    public UpdatePhoneModelDTO withId(Long id) {
        requireNonNull(id, "id must not be null");

        var dto = new UpdatePhoneModelDTO(name);
        dto.id = id;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
