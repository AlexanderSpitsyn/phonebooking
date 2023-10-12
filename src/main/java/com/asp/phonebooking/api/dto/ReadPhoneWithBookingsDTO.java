package com.asp.phonebooking.api.dto;

import java.util.List;

public class ReadPhoneWithBookingsDTO {

    private final Long id;
    private final String name;
    private final Long phoneModelId;
    private final boolean available;
    private final List<ReadPhoneBookingDTO> bookings;

    public ReadPhoneWithBookingsDTO(Long id,
                                    String name,
                                    Long phoneModelId,
                                    boolean available,
                                    List<ReadPhoneBookingDTO> bookings) {
        this.id = id;
        this.name = name;
        this.phoneModelId = phoneModelId;
        this.available = available;
        this.bookings = bookings;
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

    public boolean getAvailable() {
        return available;
    }

    public List<ReadPhoneBookingDTO> getBookings() {
        return bookings;
    }
}
