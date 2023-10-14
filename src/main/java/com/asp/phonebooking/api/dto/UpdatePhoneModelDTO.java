package com.asp.phonebooking.api.dto;

import static java.util.Objects.requireNonNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class UpdatePhoneModelDTO extends CreatePhoneModelDTO {

    @Schema(hidden = true)
    private Long id;

    public UpdatePhoneModelDTO(String brand,
                               String device,
                               String technology,
                               String bands2g,
                               String bands3g,
                               String bands4g,
                               Boolean updateWithExternal) {
        super(brand, device, technology, bands2g, bands3g, bands4g, updateWithExternal);
    }

    public UpdatePhoneModelDTO withId(Long id) {
        requireNonNull(id, "id must not be null");

        var dto = new UpdatePhoneModelDTO(
                getBrand(),
                getDevice(),
                getTechnology(),
                getBands2g(),
                getBands3g(),
                getBands4g(),
                getUpdateWithExternal()
        );
        dto.id = id;
        return dto;
    }

    public Long getId() {
        return id;
    }
}
