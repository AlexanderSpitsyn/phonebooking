package com.asp.phonebooking.api.dto;

import static java.util.Objects.requireNonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdatePhoneDTO {

    @Schema(hidden = true)
    private Long id;

    @NotNull
    @Size(min = 3, max = 300)
    private final String name;

    @NotNull
    private final Long phoneModelId;

    public UpdatePhoneDTO(String name, Long phoneModelId) {
        this.name = name;
        this.phoneModelId = phoneModelId;
    }

    public UpdatePhoneDTO withId(Long id) {
        requireNonNull(id, "id must not be null");

        var dto = new UpdatePhoneDTO(name, phoneModelId);
        dto.id = id;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPhoneModelId() {
        return phoneModelId;
    }
}
