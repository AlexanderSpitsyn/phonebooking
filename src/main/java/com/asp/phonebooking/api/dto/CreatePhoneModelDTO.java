package com.asp.phonebooking.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePhoneModelDTO {

    @NotNull
    @Size(min = 1, max = 300)
    private final String brand;

    @NotNull
    @Size(min = 1, max = 300)
    private final String device;

    @Size(min = 1, max = 300)
    private final String technology;

    @Size(min = 1, max = 300)
    private final String bands2g;

    @Size(min = 1, max = 300)
    private final String bands3g;

    @Size(min = 1, max = 300)
    private final String bands4g;

    @NotNull
    private final Boolean updateWithExternal;

    public CreatePhoneModelDTO(String brand,
                               String device,
                               String technology,
                               String bands2g,
                               String bands3g,
                               String bands4g,
                               Boolean updateWithExternal) {
        this.brand = brand;
        this.device = device;
        this.technology = technology;
        this.bands2g = bands2g;
        this.bands3g = bands3g;
        this.bands4g = bands4g;
        this.updateWithExternal = updateWithExternal;
    }

    public String getBrand() {
        return brand;
    }

    public String getDevice() {
        return device;
    }

    public String getTechnology() {
        return technology;
    }

    public String getBands2g() {
        return bands2g;
    }

    public String getBands3g() {
        return bands3g;
    }

    public String getBands4g() {
        return bands4g;
    }

    public Boolean getUpdateWithExternal() {
        return updateWithExternal;
    }
}
