package com.asp.phonebooking.api.dto;

import java.time.Instant;

public class ReadPhoneBookingDTO {

    private final Long id;
    private final String username;
    private final Instant createdAt;
    private final Instant startpoint;
    private final Instant endpoint;

    public ReadPhoneBookingDTO(Long id,
                               String username,
                               Instant createdAt,
                               Instant startpoint,
                               Instant endpoint) {
        this.id = id;
        this.username = username;
        this.createdAt = createdAt;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
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
