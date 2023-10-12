package com.asp.phonebooking.api.mapper;

import com.asp.phonebooking.api.dto.CreatePhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneWithBookingsDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneDTO;
import com.asp.phonebooking.entity.Phone;
import com.asp.phonebooking.entity.PhoneBooking;
import java.time.Instant;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PhoneBookingMapper.class)
public interface PhoneMapper {

    Phone fromCreateDto(CreatePhoneDTO dto);

    Phone fromUpdateDto(UpdatePhoneDTO dto);

    ReadPhoneDTO toReadDto(Phone phone);

    @Mapping(target = "available", source = "bookings")
    @Mapping(target = "bookings", source = "bookings")
    ReadPhoneWithBookingsDTO toReadWithBookingsDto(Phone phone, List<PhoneBooking> bookings);

    static boolean available(List<PhoneBooking> bookings) {
        var now = Instant.now();
        return bookings.stream()
                .noneMatch(booking -> !booking.getStartpoint().isAfter(now) && now.isBefore(booking.getEndpoint()));
    }
}
