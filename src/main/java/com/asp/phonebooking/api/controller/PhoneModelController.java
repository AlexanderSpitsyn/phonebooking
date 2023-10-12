package com.asp.phonebooking.api.controller;

import com.asp.phonebooking.api.dto.CreatePhoneModelDTO;
import com.asp.phonebooking.api.dto.ReadPhoneModelDTO;
import com.asp.phonebooking.api.dto.UpdatePhoneModelDTO;
import com.asp.phonebooking.api.mapper.PhoneModelMapper;
import com.asp.phonebooking.service.PhoneModelService;
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
@RequestMapping("/phone_model")
public class PhoneModelController {

    private final PhoneModelService phoneModelService;
    private final PhoneModelMapper phoneModelMapper;

    public PhoneModelController(PhoneModelService phoneModelService,
                                PhoneModelMapper phoneModelMapper) {
        this.phoneModelService = phoneModelService;
        this.phoneModelMapper = phoneModelMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadPhoneModelDTO create(@RequestBody @Valid CreatePhoneModelDTO phoneModelDTO) {
        var phoneModel = phoneModelMapper.fromCreateDto(phoneModelDTO);
        phoneModel = phoneModelService.create(phoneModel);
        return phoneModelMapper.toReadDto(phoneModel);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ReadPhoneModelDTO update(@PathVariable Long id, @RequestBody @Valid UpdatePhoneModelDTO phoneModelDTO) {
        var phoneModel = phoneModelMapper.fromUpdateDto(phoneModelDTO.withId(id));
        phoneModel = phoneModelService.update(phoneModel);
        return phoneModelMapper.toReadDto(phoneModel);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        phoneModelService.delete(id);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ReadPhoneModelDTO get(@PathVariable Long id) {
        var phoneModel = phoneModelService.get(id);
        return phoneModelMapper.toReadDto(phoneModel);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<ReadPhoneModelDTO> getAll() {
        return StreamSupport.stream(phoneModelService.findAll().spliterator(), false)
                .map(phoneModelMapper::toReadDto)
                .toList();
    }
}
