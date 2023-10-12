package com.asp.phonebooking.api.mapper;

import com.asp.phonebooking.api.dto.CreatePhoneBookingDTO;
import com.asp.phonebooking.api.dto.ReadPhoneBookingDTO;
import com.asp.phonebooking.api.dto.ReadPhoneBookingForCurrentUserDTO;
import com.asp.phonebooking.entity.PhoneBooking;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneBookingMapper {

    PhoneBooking fromCreateDto(CreatePhoneBookingDTO dto);

    ReadPhoneBookingForCurrentUserDTO toReadForCurrentUserDto(PhoneBooking phoneBooking);

    ReadPhoneBookingDTO toReadDto(PhoneBooking phoneBooking);
}
