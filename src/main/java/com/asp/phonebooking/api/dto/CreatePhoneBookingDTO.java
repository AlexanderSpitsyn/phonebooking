package com.asp.phonebooking.api.dto;

import static java.util.Objects.requireNonNull;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;

public class CreatePhoneBookingDTO {

    @NotNull
    private final Long phoneId;

    @NotNull
    private final Instant startpoint;

    @NotNull
    private final Instant endpoint;

    @Schema(hidden = true)
    private String username;

    public CreatePhoneBookingDTO(Long phoneId, Instant startpoint, Instant endpoint) {
        this.phoneId = phoneId;
        this.startpoint = startpoint;
        this.endpoint = endpoint;
    }

    public Long getPhoneId() {
        return phoneId;
    }

    public Instant getStartpoint() {
        return startpoint;
    }

    public Instant getEndpoint() {
        return endpoint;
    }

    public String getUsername() {
        return username;
    }

    public CreatePhoneBookingDTO withUsername(String username) {
        requireNonNull(username, "username must not be null");

        var phoneBookingDTO = new CreatePhoneBookingDTO(phoneId, startpoint, endpoint);
        phoneBookingDTO.username = username;
        return phoneBookingDTO;
    }
}
