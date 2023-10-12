package com.asp.phonebooking.api.mapper;

import com.asp.phonebooking.api.dto.CreatePhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneWithBookingsDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneDTO;
import com.asp.phonebooking.entity.Phone;
import org.mapstruct.Mapper;

@Mapper(uses = PhoneModelMapper.class)
public interface PhoneMapper {

    Phone fromCreateDto(CreatePhoneDTO dto);

    Phone fromUpdateDto(UpdatePhoneDTO dto);

    ReadPhoneDTO toReadDto(Phone phone);
}
