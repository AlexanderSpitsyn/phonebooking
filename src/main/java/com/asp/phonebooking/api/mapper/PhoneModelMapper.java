package com.asp.phonebooking.api.mapper;

import com.asp.phonebooking.api.dto.CreatePhoneModelDTO;
import com.asp.phonebooking.api.dto.ReadPhoneModelDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneModelDTO;
import com.asp.phonebooking.entity.PhoneModel;
import org.mapstruct.Mapper;

@Mapper
public interface PhoneModelMapper {

    PhoneModel fromCreateDto(CreatePhoneModelDTO dto);

    PhoneModel fromUpdateDto(UpdatePhoneModelDTO dto);

    ReadPhoneModelDTO toReadDto(PhoneModel phoneModel);
}
