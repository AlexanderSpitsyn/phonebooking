package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.api.dto.CreatePhoneBookingDTO;
import com.asp.phonebooking.api.dto.ReadPhoneBookingForCurrentUserDTO;
import com.asp.phonebooking.api.mapper.PhoneBookingMapper;
import com.asp.phonebooking.service.PhoneBookingService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone_booking")
public class PhoneBookingController {

    private final PhoneBookingService phoneBookingService;
    private final PhoneBookingMapper phoneBookingMapper;

    public PhoneBookingController(PhoneBookingService phoneBookingService,
                                  PhoneBookingMapper phoneBookingMapper) {
        this.phoneBookingService = phoneBookingService;
        this.phoneBookingMapper = phoneBookingMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ReadPhoneBookingForCurrentUserDTO create(@RequestBody @Valid CreatePhoneBookingDTO phoneBookingDTO,
                                                    @AuthenticationPrincipal User user) {
        var createPhoneBookingDTO = phoneBookingDTO.withUsername(user.getUsername());
        var phoneBooking = phoneBookingMapper.fromCreateDto(createPhoneBookingDTO);
        phoneBooking = phoneBookingService.create(phoneBooking);
        return phoneBookingMapper.toReadForCurrentUserDto(phoneBooking);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public void delete(@PathVariable Long id, @AuthenticationPrincipal User user) {
        var phoneBooking = phoneBookingService.find(id);
        if (phoneBooking.isEmpty()) {
            return;
        }

        if (!phoneBooking.get().getUsername().equals(user.getUsername())) {
            return;
        }

        phoneBookingService.delete(id);
    }
}
