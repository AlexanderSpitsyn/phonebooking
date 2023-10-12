package com.asp.phonebooking.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.asp.phonebooking.entity.Phone;
import com.asp.phonebooking.exception.NotFoundException;
import com.asp.phonebooking.repository.PhoneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PhoneServiceTest {

    private PhoneService phoneService;
    private PhoneModelService phoneModelService;
    private PhoneRepository phoneRepository;

    @BeforeEach
    public void setUp() {
        phoneRepository = mock(PhoneRepository.class);
        phoneModelService = mock(PhoneModelService.class);
        phoneService = new PhoneService(phoneRepository, phoneModelService);
    }

    @Test
    void create_phoneModelDoesntExist() {
        long phoneModelId = 1;
        when(phoneRepository.existsById(phoneModelId)).thenReturn(false);

        var phone = mock(Phone.class);
        when(phone.getId()).thenReturn(null);
        when(phone.getName()).thenReturn("test phone");
        when(phone.getPhoneModelId()).thenReturn(phoneModelId);

        assertThatThrownBy(() -> phoneService.create(phone))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void update_phoneModelDoesntExist() {
        long phoneModelId = 2;
        when(phoneModelService.existsById(phoneModelId)).thenReturn(false);

        var phone = mock(Phone.class);
        when(phone.getName()).thenReturn("test phone");
        when(phone.getPhoneModelId()).thenReturn(phoneModelId);

        assertThatThrownBy(() -> phoneService.update(phone))
                .isInstanceOf(NotFoundException.class);
    }
}