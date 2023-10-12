package com.asp.phonebooking.api.dto;

import java.time.Instant;

public class ReadPhoneBookingForCurrentUserDTO {

    private final Long id;
    private final Long phoneId;
    private final Instant createdAt;
    private final Instant startpoint;
    private final Instant endpoint;

    public ReadPhoneBookingForCurrentUserDTO(Long id,
                                             Long phoneId,
                                             Instant createdAt,
                                             Instant startpoint,
                                             Instant endpoint) {
        this.id = id;
        this.phoneId = phoneId;
        this.createdAt = createdAt;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getStartpoint() {
        return startpoint;
    }

    public Instant getEndpoint() {
        return endpoint;
    }

}
