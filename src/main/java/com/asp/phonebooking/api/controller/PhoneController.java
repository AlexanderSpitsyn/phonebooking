package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.api.dto.CreatePhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneDTO;
import com.asp.phonebooking.api.dto.ReadPhoneWithBookingsDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneDTO;
import com.asp.phonebooking.api.mapper.PhoneMapper;
import com.asp.phonebooking.service.PhoneService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/phone")
public class PhoneController {

    private final PhoneService phoneService;
    private final PhoneMapper phoneMapper;

    public PhoneController(PhoneService phoneService,
                           PhoneMapper phoneMapper) {
        this.phoneService = phoneService;
        this.phoneMapper = phoneMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadPhoneDTO create(@RequestBody @Valid CreatePhoneDTO phoneDTO) {
        var phone = phoneMapper.fromCreateDto(phoneDTO);
        phone = phoneService.create(phone);
        return phoneMapper.toReadDto(phone);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadPhoneDTO update(@PathVariable Long id, @RequestBody @Valid UpdatePhoneDTO phoneDTO) {
        var phone = phoneMapper.fromUpdateDto(phoneDTO.withId(id));
        phone = phoneService.update(phone);
        return phoneMapper.toReadDto(phone);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        phoneService.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ReadPhoneDTO> getAll() {
        return StreamSupport.stream(phoneService.findAll().spliterator(), false)
                .map(phoneMapper::toReadDto)
                .toList();
    }
}
