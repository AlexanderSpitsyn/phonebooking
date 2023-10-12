package com.asp.phonebooking.api.dto;

public class ReadPhoneDTO {

    private final Long id;
    private final String name;
    private final Long phoneModelId;

    public ReadPhoneDTO(Long id, String name, Long phoneModelId) {
        this.id = id;
        this.name = name;
        this.phoneModelId = phoneModelId;
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
