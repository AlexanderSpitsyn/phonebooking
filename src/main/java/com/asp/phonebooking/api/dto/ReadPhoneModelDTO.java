package com.asp.phonebooking.api.dto;

public class ReadPhoneModelDTO {

    private final Long id;
    private final String brand;
    private final String device;
    private final String technology;
    private final String bands2g;
    private final String bands3g;
    private final String bands4g;
    private final Boolean updateWithExternal;

    public ReadPhoneModelDTO(Long id,
                             String brand,
                             String device,
                             String technology,
                             String bands2g,
                             String bands3g,
                             String bands4g,
                             Boolean updateWithExternal) {
        this.id = id;
        this.brand = brand;
        this.device = device;
        this.technology = technology;
        this.bands2g = bands2g;
        this.bands3g = bands3g;
        this.bands4g = bands4g;
        this.updateWithExternal = updateWithExternal;
    }

    public Long getId() {
        return id;
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
